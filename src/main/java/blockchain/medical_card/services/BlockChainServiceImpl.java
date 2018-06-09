package blockchain.medical_card.services;

import blockchain.medical_card.api.BlockChainService;
import blockchain.medical_card.api.CommunicationService;
import blockchain.medical_card.api.dao.BlockDao;
import blockchain.medical_card.dto.CheckResultDTO;
import blockchain.medical_card.dto.IllnessRecordBlock;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.RecordBlockChain;
import blockchain.medical_card.helpers.RecordBlockHelper;
import com.mongodb.client.FindIterable;
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
    private RecordBlockHelper recordBlockHelper;

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
        IllnessRecordBlock block = new IllnessRecordBlock(Instant.now().getEpochSecond(), records, blockChain.getLatestBlock().getPreviousHash());
        blockChain.mineBlock(block);
        communicationService.notifyAllNodesToCheck(block);
        blockChain.addBlock(block);
        Document document = recordBlockHelper.mapRecordBlock(block);
        blockDao.addDocument(document);
        return block;
    }

    @Override
    public LinkedList<IllnessRecordBlock> getBlocks() {
        LinkedList<IllnessRecordBlock> recordBlockList = new LinkedList<>();
        FindIterable<Document> documents = blockDao.getDocuments();
        for (Document document : documents)
            recordBlockList.add(recordBlockHelper.mapDocument(document));
        return recordBlockList;
    }

    @Override
    public CheckResultDTO checkBlock(IllnessRecordBlock block) {
        String from = communicationService.getCurrent();
        if (block == null)
            return new CheckResultDTO(CheckResultDTO.Result.INVALID_BLOCK, from);

        IllnessRecordBlock latestBlock = blockChain.getLatestBlock();
        if (latestBlock == null)
            return new CheckResultDTO(CheckResultDTO.Result.INVALID_CURRENT_BLOCKCHAIN, from);

        if (!StringUtils.equals(latestBlock.getHash(), block.getPreviousHash()) || !StringUtils.startsWith(block.getHash(), blockChain.getTargetHashPrefix()))
            return new CheckResultDTO(CheckResultDTO.Result.NOT_ACCEPTABLE, from);

        return new CheckResultDTO(CheckResultDTO.Result.ACCEPTABLE, from);
    }

    @Override
    public void addNewBlock(IllnessRecordBlock block) {
        if (block == null) {
            throw new IllegalArgumentException("Block is mandatory");
        }
        if (StringUtils.isAnyEmpty(block.getHash(), block.getPreviousHash())) {
            throw new IllegalArgumentException("Invalid block");
        }
        blockChain.addBlock(block);
    }
}
