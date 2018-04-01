package blockchain.medical_card.block;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
	private String fromAddress;
	private String toAddress;
	private Double amount;
}
