package blockchain.medical_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Data
public class DoctorDTO extends AbstractPerson {
	private String username;
	private String email;
	private String passwordHash;
	private String password;
}
