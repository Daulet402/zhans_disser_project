package blockchain.medical_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IllnessRecordDTO implements Serializable {
	private String id;
	private String plan;
	private String doctorId;
	private String diagnosis;
	private String complaints;
	private String illnessHistory;
	private String inspectionType;
	private LocalDateTime visitTime;
}
