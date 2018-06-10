package blockchain.medical_card.services.fx;

import blockchain.medical_card.api.dao.CommonDaoService;
import blockchain.medical_card.api.dao.PatientDaoService;
import blockchain.medical_card.api.fx.PatientRegistrationService;
import blockchain.medical_card.dto.AddressDTO;
import blockchain.medical_card.dto.PatientDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.info.CityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientRegistrationServiceImpl implements PatientRegistrationService {

	@Autowired
	private PatientDaoService patientDaoService;

	@Autowired
	private CommonDaoService commonDaoService;

	@Override
	public List<CityDTO> getAllCities(){
		return commonDaoService.getAllCities();
	}

	@Override
	public void registerPatient(
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
			Double weight) throws BlockChainAppException {

		PatientDTO patientDTO = new PatientDTO();
		patientDTO.setIin(iin);
		patientDTO.setHeight(height);
		patientDTO.setWeight(weight);
		patientDTO.setLastName(lastName);
		patientDTO.setFirstName(firstName);
		patientDTO.setWorkPlace(workPlace);
		patientDTO.setBloodType(bloodType);
		patientDTO.setHospitalId(hospitalId);
		patientDTO.setMiddleName(middleName);
		patientDTO.setPhoneNumber(phoneNumber);
		patientDTO.setAddress(new AddressDTO(cityId, districtId, address));

		patientDaoService.addPatient(patientDTO);
	}
}
