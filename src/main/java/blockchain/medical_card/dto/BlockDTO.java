package blockchain.medical_card.dto;

import blockchain.medical_card.block.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockDTO {
	private Long timestamp;
	private String previousHash;
	private String data;
}
