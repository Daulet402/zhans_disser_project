package blockchain.medical_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IllnessRecordDTO implements Serializable{
	private String complaints;
	private String illnessHistory;
	private String diagnosis;
	private String plan;
	private String inspectionType;
}
