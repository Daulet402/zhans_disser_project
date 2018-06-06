package blockchain.medical_card.api.dao;

import com.mongodb.client.FindIterable;
import org.bson.Document;

public interface BlockDao {

    void addDocument(Document document);

    FindIterable<Document> getDocuments();
}
