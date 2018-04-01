package blockchain.medical_card.dto.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
	private Long cityId;
	private String name;
	private String code;
	private List<DistrictDTO> districtDTOList;

	@Override
	public String toString() {
		return getName();
	}
}
