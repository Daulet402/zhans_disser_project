package blockchain.medical_card.api.fx;

import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.info.CityDTO;

import java.util.List;

public interface RegistrationService {

	List<CityDTO> getAllCities();

	void registerDoctor(String firstName,
						String lastName,
						String middleName,
						String iin,
						String email,
						String username,
						Long hospitalId) throws BlockChainAppException;
}
