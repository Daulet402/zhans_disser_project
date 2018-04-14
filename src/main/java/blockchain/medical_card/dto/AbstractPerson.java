package blockchain.medical_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractPerson implements Serializable{
	private String firstName;
	private String lastName;
	private String middleName;
	private String iin;
	private Long hospitalId;
	private AddressDTO address;

	public String getFullName() {
		return String.format("%s %s %s", lastName, firstName, middleName);
	}
}
