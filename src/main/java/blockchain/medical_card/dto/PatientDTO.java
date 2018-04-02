package blockchain.medical_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO extends AbstractPerson {
	private String bloodType;
	private Double height;
	private Double weight;
	private String phoneNumber;
	private String workPlace;
}
