package blockchain.medical_card.api.dao;

import blockchain.medical_card.dto.PatientDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;

import java.util.List;

public interface PatientDaoService {

	void addPatient(PatientDTO patient) throws BlockChainAppException;

	List<PatientDTO> getAllPatients() throws BlockChainAppException;

	List<PatientDTO> getPatientsByHospitalId(Long hospitalId) throws BlockChainAppException;

	PatientDTO findPatientByPersonalInfo(String firstName, String lastName, String iin) throws BlockChainAppException;
}
