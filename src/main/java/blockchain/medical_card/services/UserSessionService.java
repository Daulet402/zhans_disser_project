package blockchain.medical_card.services;

import blockchain.medical_card.dto.DoctorDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserSessionService {

	private DoctorDTO doctor;

	public boolean isSessionOfUserExpired(DoctorDTO doctor) {
		return false;
	}
}
