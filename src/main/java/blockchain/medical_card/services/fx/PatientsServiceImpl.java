package blockchain.medical_card.services.fx;

import blockchain.medical_card.api.dao.CommonDaoService;
import blockchain.medical_card.api.dao.PatientDaoService;
import blockchain.medical_card.api.fx.PatientsService;
import blockchain.medical_card.dto.PatientDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.info.CityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientsServiceImpl implements PatientsService {

	@Autowired
	private PatientDaoService patientDaoService;

	@Autowired
	private CommonDaoService commonDaoService;

	@Override
	public List<CityDTO> getAllCities() throws BlockChainAppException {
		return commonDaoService.getAllCities();
	}

	@Override
	public List<PatientDTO> getPatientsByHospitalId(Long hospitalId) throws BlockChainAppException {
		return patientDaoService.getPatientsByHospitalId(hospitalId);
	}
}
