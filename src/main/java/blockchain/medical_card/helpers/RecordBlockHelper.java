package blockchain.medical_card.helpers;

import blockchain.medical_card.dto.IllnessRecordBlock;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecordBlockHelper {

    public IllnessRecordBlock mapDocument(Document document) {
        List<IllnessRecordDTO> illnessRecords = new ArrayList<>();
        if (document.containsKey("illnessRecords"))
            illnessRecords = JsonUtils.fromJsonToType(
                    document.get("illnessRecords").toString(),
                    new TypeToken<ArrayList<IllnessRecordDTO>>() {
                    }.getType()
            );


        IllnessRecordBlock recordBlock = new IllnessRecordBlock();
        recordBlock.setIllnessRecords(illnessRecords);
        recordBlock.setHash(document.getString("hash"));
        recordBlock.setNonce(document.getLong("nonce"));
        recordBlock.setTimestamp(document.getLong("timestamp"));
        recordBlock.setPreviousHash(document.getString("previousHash"));
        recordBlock.setIsGenesisBlock(document.getBoolean("isGenesisBlock"));
        return recordBlock;
    }

    public Document mapRecordBlock(IllnessRecordBlock recordBlock) {
        Document document = new Document();
        document.put("hash", recordBlock.getHash());
        document.put("nonce", recordBlock.getNonce());
        document.put("timestamp", recordBlock.getTimestamp());
        document.put("previousHash", recordBlock.getPreviousHash());
        document.put("isGenesisBlock", recordBlock.getIsGenesisBlock());
        document.put("illnessRecords", JsonUtils.toJson(recordBlock.getIllnessRecords()));
        return document;
    }
}
