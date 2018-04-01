package blockchain.medical_card.dto.info;

import blockchain.medical_card.dto.HospitalDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDTO {
	private Long districtId;
	private String name;
	private Long cityId;
	private List<HospitalDTO> hospitalDTOList;

	@Override
	public String toString() {
		return getName();
	}
}
