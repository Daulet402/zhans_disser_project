package common;

import blockchain.medical_card.configuration.DataSourceConfiguration;
import blockchain.medical_card.configuration.PropertiesConfig;
import blockchain.medical_card.dto.IllnessRecordBlock;
import blockchain.medical_card.helpers.RecordBlockHelper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfiguration.class, PropertiesConfig.class})
@PropertySource("classpath:app.datasource.properties")
public class TestApp {

    //    @Autowired
    private RecordBlockHelper recordBlockHelper = new RecordBlockHelper();

    @Autowired
    private MongoDatabase mongoDatabase;


    @Test
    public void testRecordBlocks() {
        MongoCollection<Document> recordBlocks = mongoDatabase.getCollection("record_blocks");
        FindIterable<Document> documents = recordBlocks.find();
        for (Document document : documents) {
            IllnessRecordBlock recordBlock = recordBlockHelper.mapDocument(document);
            Assert.assertNotNull(recordBlock);
        }
    }
}
