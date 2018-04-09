package blockchain.medical_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PatientDTO extends AbstractPerson {
	private String id;
	private String bloodType;
	private Double height;
	private Double weight;
	private String phoneNumber;
	private String workPlace;
	private List<IllnessRecordDTO> illnessRecordList;

	public List<IllnessRecordDTO> getIllnessRecordList() {
		if (illnessRecordList == null)
			illnessRecordList = new ArrayList<>();

		return illnessRecordList;
	}
}
