package common;

import blockchain.medical_card.configuration.DataSourceConfiguration;
import blockchain.medical_card.configuration.PropertiesConfig;
import blockchain.medical_card.dto.IllnessRecordBlock;
import blockchain.medical_card.helpers.RecordBlockHelper;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfiguration.class, PropertiesConfig.class})
@PropertySource("classpath:app.datasource.properties")
public class TestApp {

    //@Autowired
    private RecordBlockHelper recordBlockHelper = new RecordBlockHelper();


    @Test
    public void testMongoDb() {
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoDatabase database = mongo.getDatabase("test");
    }

    @Test
    public void testRecordBlocks() {
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoDatabase database = mongo.getDatabase("medical_card");
        MongoCollection<Document> recordBlocks = database.getCollection("record_blocks");
        FindIterable<Document> documents = recordBlocks.find();
        for (Document document : documents) {
            IllnessRecordBlock recordBlock = recordBlockHelper.mapDocument(document);
            Assert.assertNotNull(recordBlock);
        }
    }
}
