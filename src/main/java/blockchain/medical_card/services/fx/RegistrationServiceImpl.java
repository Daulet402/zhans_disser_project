package blockchain.medical_card.services.fx;

import blockchain.medical_card.api.dao.CommonDaoService;
import blockchain.medical_card.api.dao.DoctorDaoService;
import blockchain.medical_card.api.fx.RegistrationService;
import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.info.CityDTO;
import blockchain.medical_card.utils.AlgorithmUtils;
import blockchain.medical_card.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private DoctorDaoService doctorDaoService;

	@Autowired
	private CommonDaoService commonDaoService;

	@Override
	public List<CityDTO> getAllCities() {
		return commonDaoService.getAllCities();
	}

	@Override
	public void registerDoctor(
			String firstName,
			String lastName,
			String middleName,
			String iin,
			String email,
			String username,
			Long hospitalId) throws BlockChainAppException {

		String pass = CommonUtils.getRandomString();

		DoctorDTO doctorDTO = new DoctorDTO();
		doctorDTO.setIin(iin);
		doctorDTO.setEmail(email);
		doctorDTO.setLastName(lastName);
		doctorDTO.setUsername(username);
		//doctorDTO.setId(AlgorithmUtils.getUniqKey());
		doctorDTO.setFirstName(firstName);
		doctorDTO.setMiddleName(middleName);
		doctorDTO.setHospitalId(hospitalId);
		doctorDTO.setPasswordHash(AlgorithmUtils.applySha256(pass));
		doctorDTO.setPassword(pass); // TODO: 04/14/2018 send password to email and remove pass field

		doctorDaoService.addDoctor(doctorDTO);
	}
}
