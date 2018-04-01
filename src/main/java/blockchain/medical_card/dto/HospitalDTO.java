package blockchain.medical_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDTO {
	private Long hospitalId;
	private String name;
	private Long districtId;
	//private AddressDTO address;
	private String address;

	@Override
	public String toString() {
		return getName();
	}
}
