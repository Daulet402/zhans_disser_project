package blockchain.medical_card.dto;

import blockchain.medical_card.dto.info.CityDTO;
import blockchain.medical_card.dto.info.DistrictDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
	private Long cityId;
	private Long districtId;
	private String address;
}
