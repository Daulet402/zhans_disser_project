package blockchain.medical_card.block;

import blockchain.medical_card.utils.AlgorithmUtils;
import blockchain.medical_card.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Block {
	private String hash;
	private Long timestamp;
	private String previousHash;
	private List<Transaction> transactions;
	private int nonce;

	public Block(Long timestamp, List<Transaction> transactions, String previousHash) {
		this.timestamp = timestamp;
		this.previousHash = previousHash;
		this.transactions = transactions;
		this.hash = calculateHash();
	}

	public String calculateHash() {
		return AlgorithmUtils.applySha256(previousHash +
				String.valueOf(timestamp) +
				JsonUtils.toJson(transactions) +
				String.valueOf(nonce));
	}

	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0');
		while (!StringUtils.equals(StringUtils.substring(hash, 0, difficulty), target)) {
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block mined!!! " + hash);
	}
}
