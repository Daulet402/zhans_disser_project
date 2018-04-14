package blockchain.medical_card.api.fx;

import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.info.CityDTO;

import java.util.List;

public interface PatientRegistrationService {

	List<CityDTO> getAllCities();

	void registerPatient(
			String firstName,
			String lastName,
			String middleName,
			String iin,
			String bloodType,
			String phoneNumber,
			String workPlace,
			Long hospitalId,
			Long cityId,
			Long districtId,
			String address,
			Double height,
			Double weight
	) throws BlockChainAppException;
}
