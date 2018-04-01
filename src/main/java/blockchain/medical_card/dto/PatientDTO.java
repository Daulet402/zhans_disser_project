package blockchain.medical_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO extends AbstractPerson {
	private String bloodType;
	private String height;
	private String weight;
	private String phoneNumber;
	private String workPlace;
}
