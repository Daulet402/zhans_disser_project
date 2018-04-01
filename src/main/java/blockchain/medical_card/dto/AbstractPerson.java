package blockchain.medical_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractPerson {
	private String firstName;
	private String lastName;
	private String middleName;
	private String iin;
	private AddressDTO address;
}
