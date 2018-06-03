package blockchain.medical_card.dto;

import blockchain.medical_card.configuration.LocalDateTimeDeserializer;
import blockchain.medical_card.configuration.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
	private String patientId;
	private String diagnosis;
	private String complaints;
	private String illnessHistory;
	private String inspectionType;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime visitTime;
	// TODO: 05/30/2018 save in data who is receiver and who is sender
}
