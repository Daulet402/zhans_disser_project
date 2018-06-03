package blockchain.medical_card.services.dao;

import blockchain.medical_card.api.FileService;
import blockchain.medical_card.api.dao.BlockDao;
import blockchain.medical_card.dto.IllnessRecordBlock;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.PatientDTO;
import blockchain.medical_card.dto.RecordBlockChain;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.helpers.RecordBlockHelper;
import blockchain.medical_card.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.List;

@Component
public class BlockDaoImpl implements BlockDao {

    @Autowired
    private FileService fileService;

    @Autowired
    private RecordBlockHelper recordBlockHelper;

    private RecordBlockChain blockChain;

    @PostConstruct
    public void init() {
        blockChain = new RecordBlockChain();
    }

    @Override
    public void addRecords(List<IllnessRecordDTO> records) throws BlockChainAppException {
        IllnessRecordBlock block = new IllnessRecordBlock(Instant.now().getEpochSecond(), records, blockChain.getLatestBlock().getPreviousHash());
        System.out.println("Mining block ....");
        blockChain.addBlock(block);
        fileService.writeToFile(getBlocksFileName(), JsonUtils.toJson(blockChain.getBlocks()));

        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoDatabase database = mongo.getDatabase("medical_card");
        MongoCollection<Document> recordBlocks = database.getCollection("record_blocks");


        Document document = recordBlockHelper.mapRecordBlock(block);
        recordBlocks.insertOne(document);
    }

    @Override
    public List<IllnessRecordBlock> getBlocks() throws BlockChainAppException {
        return JsonUtils
                .getGson()
                .fromJson(fileService.readFromFile(getBlocksFileName()), new TypeToken<List<PatientDTO>>() {
                }.getType());
    }

    private String getBlocksFileName() {
        return "C:\\blockchain\\blocks\\new_blocks.json";
    }
}
