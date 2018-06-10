package blockchain.medical_card.services;

import blockchain.medical_card.api.BlockChainService;
import blockchain.medical_card.api.CommunicationService;
import blockchain.medical_card.api.dao.BlockDao;
import blockchain.medical_card.dto.IllnessRecordBlock;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.RecordBlockChain;
import blockchain.medical_card.dto.ResultDTO;
import blockchain.medical_card.mappers.RecordBlockMapper;
import com.mongodb.client.FindIterable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

@Service
public class BlockChainServiceImpl implements BlockChainService {

    private RecordBlockChain blockChain;

    @Autowired
    private RecordBlockMapper recordBlockMapper;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private CommunicationService communicationService;

    @PostConstruct
    public void init() {
        blockChain = new RecordBlockChain();
        blockChain.getBlocks().addAll(getBlocks());
    }


    @Override
    public IllnessRecordBlock addRecords(List<IllnessRecordDTO> records) {
        IllnessRecordBlock block = new IllnessRecordBlock(Instant.now().getEpochSecond(), records, blockChain.getLatestBlock().getHash());
        blockChain.mineBlock(block);
        List<ResultDTO> checkResults = communicationService.notifyAllNodesToCheck(block);
        if (CollectionUtils.isEmpty(checkResults)) {
            throw new RuntimeException("No check results");
        }
        for (ResultDTO checkResult : checkResults) {
            System.out.println(String.format("checkResult = %s %s", checkResult.getFrom(), checkResult.getResultCode()));
        }
        long acceptableCount = checkResults.stream()
                .map(ResultDTO::getResultCode)
                .filter(result -> result == ResultDTO.ResultCode.ACCEPTABLE)
                .count();

        double acceptablePercentage = (double) acceptableCount / checkResults.size();
        System.out.println("acceptablePercentage " + acceptablePercentage);
        if (acceptablePercentage >= 0.5) {
            blockChain.addBlock(block);
            List<ResultDTO> addResults = communicationService.notifyAllNodesToAdd(block);
            if (CollectionUtils.isEmpty(addResults)) {
                throw new RuntimeException("No add results");
            }
            for (ResultDTO addResult : addResults) {
                System.out.println(String.format("addResult = %s %s", addResult.getFrom(), addResult.getResultCode()));
            }
            Document document = recordBlockMapper.mapRecordBlock(block);
            //blockDao.addDocument(document);
        } else {
            System.out.println("AcceptablePercentage is too low to add new block. Try again");
        }
        return block;
    }

    @Override
    public LinkedList<IllnessRecordBlock> getBlocks() {
        LinkedList<IllnessRecordBlock> recordBlockList = new LinkedList<>();
        FindIterable<Document> documents = blockDao.getDocuments();
        for (Document document : documents)
            recordBlockList.add(recordBlockMapper.mapDocument(document));
        return recordBlockList;
    }

    @Override
    public ResultDTO checkBlock(IllnessRecordBlock block) {
        String from = communicationService.getCurrent();
        if (block == null)
            return new ResultDTO(ResultDTO.ResultCode.INVALID_BLOCK, from);

        IllnessRecordBlock latestBlock = blockChain.getLatestBlock();
        if (latestBlock == null)
            return new ResultDTO(ResultDTO.ResultCode.INVALID_CURRENT_BLOCKCHAIN, from);

        System.out.println(String.format("my latest block's hash is %s and new block's prev. hash is %s", latestBlock.getHash(), block.getPreviousHash()));
        if (!StringUtils.equals(latestBlock.getHash(), block.getPreviousHash()) || !StringUtils.startsWith(block.getHash(), blockChain.getTargetHashPrefix()))
            return new ResultDTO(ResultDTO.ResultCode.NOT_ACCEPTABLE, from);

        return new ResultDTO(ResultDTO.ResultCode.ACCEPTABLE, from);
    }

    @Override
    public ResultDTO addNewBlock(IllnessRecordBlock block) {
        String from = communicationService.getCurrent();
        if (block == null || StringUtils.isAnyEmpty(block.getHash(), block.getPreviousHash())) {
            return new ResultDTO(ResultDTO.ResultCode.INVALID_BLOCK, from);
        }
        blockChain.addBlock(block);
        return new ResultDTO(ResultDTO.ResultCode.ADDED, from);
    }
}
