package blockchain.medical_card.services.dao;

import blockchain.medical_card.api.dao.BlockDao;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlockDaoImpl implements BlockDao {

    public static final String RECORD_BLOCK_COLLECTION_NAME = "record_blocks";

    @Autowired
    private MongoDatabase mongoDatabase;

    @Override
    public void addDocument(Document document) {
        MongoCollection<Document> recordBlocks = mongoDatabase.getCollection(RECORD_BLOCK_COLLECTION_NAME);
        recordBlocks.insertOne(document);
    }

    @Override
    public FindIterable<Document> getDocuments() {
        FindIterable<Document> documents = mongoDatabase.getCollection(RECORD_BLOCK_COLLECTION_NAME).find();
        return documents;
    }
}
