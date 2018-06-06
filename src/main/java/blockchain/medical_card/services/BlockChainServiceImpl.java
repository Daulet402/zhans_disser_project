package blockchain.medical_card.services;

import blockchain.medical_card.api.BlockChainService;
import blockchain.medical_card.api.dao.BlockDao;
import blockchain.medical_card.dto.IllnessRecordBlock;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.RecordBlockChain;
import blockchain.medical_card.helpers.RecordBlockHelper;
import com.mongodb.client.FindIterable;
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

    @PostConstruct
    public void init() {
        blockChain = new RecordBlockChain();
        blockChain.getBlocks().addAll(getBlocks());
    }


    @Override
    public IllnessRecordBlock addRecords(List<IllnessRecordDTO> records) {
        IllnessRecordBlock block = new IllnessRecordBlock(Instant.now().getEpochSecond(), records, blockChain.getLatestBlock().getPreviousHash());
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
    public void notifyAllNodes() {

    }
}
