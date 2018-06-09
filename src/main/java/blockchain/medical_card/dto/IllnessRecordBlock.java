package blockchain.medical_card.dto;

import blockchain.medical_card.api.Block;
import blockchain.medical_card.utils.AlgorithmUtils;
import blockchain.medical_card.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IllnessRecordBlock implements Block {
    private String hash;
    private long nonce;
    private Long timestamp;
    private String previousHash;
    private Boolean isGenesisBlock = false;
    private List<IllnessRecordDTO> illnessRecords;

    public IllnessRecordBlock(Long timestamp, List<IllnessRecordDTO> illnessRecords, String previousHash) {
        this.timestamp = timestamp;
        this.illnessRecords = illnessRecords;
        this.previousHash = previousHash;
    }

    public String calculateHash() {
        return AlgorithmUtils.applySha256(previousHash +
                String.valueOf(timestamp) +
                JsonUtils.toJson(illnessRecords) +
                String.valueOf(nonce));
    }

    public void mineBlock(int difficulty) {
        System.out.println("Mining block ....");
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!StringUtils.equals(StringUtils.substring(hash, 0, difficulty), target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined!!! " + hash);
    }
}
