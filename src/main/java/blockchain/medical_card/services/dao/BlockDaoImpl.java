package blockchain.medical_card.services.dao;

import blockchain.medical_card.api.FileService;
import blockchain.medical_card.api.dao.BlockDao;
import blockchain.medical_card.dto.IllnessRecordBlock;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.RecordBlockChain;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.helpers.RecordBlockHelper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

@Component
public class BlockDaoImpl implements BlockDao {

    public static final String RECORD_BLOCK_COLLECTION_NAME = "record_blocks";
    @Autowired
    private FileService fileService;

    @Autowired
    private RecordBlockHelper recordBlockHelper;

    @Autowired
    private MongoDatabase mongoDatabase;

    private RecordBlockChain blockChain;

    @PostConstruct
    public void init() {
        blockChain = new RecordBlockChain();
    }

    @Override
    public void addRecords(List<IllnessRecordDTO> records) throws BlockChainAppException {
        IllnessRecordBlock block = new IllnessRecordBlock(Instant.now().getEpochSecond(), records, blockChain.getLatestBlock().getPreviousHash());
        blockChain.addBlock(block);

        MongoCollection<Document> recordBlocks = mongoDatabase.getCollection(RECORD_BLOCK_COLLECTION_NAME);
        Document document = recordBlockHelper.mapRecordBlock(block);
        recordBlocks.insertOne(document);
    }

    @Override
    public LinkedList<IllnessRecordBlock> getBlocks() throws BlockChainAppException {
        LinkedList<IllnessRecordBlock> recordBlockList = new LinkedList<>();
        FindIterable<Document> documents = mongoDatabase.getCollection(RECORD_BLOCK_COLLECTION_NAME).find();
        for (Document document : documents)
            recordBlockList.add(recordBlockHelper.mapDocument(document));
        return recordBlockList;
    }
}
