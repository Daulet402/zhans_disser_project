package blockchain.medical_card.api.fx;

import blockchain.medical_card.dto.PatientDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.info.CityDTO;

import java.util.List;

public interface PatientsService {

	List<CityDTO> getAllCities() throws BlockChainAppException;

	List<PatientDTO> getPatientsByHospitalId(Long hospitalId) throws BlockChainAppException;
}
