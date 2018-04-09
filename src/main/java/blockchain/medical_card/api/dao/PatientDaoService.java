package blockchain.medical_card.api.dao;

import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.PatientDTO;
import blockchain.medical_card.dto.exceptions.BlockchainAppException;

import java.util.List;

public interface PatientDaoService {

	List<PatientDTO> getAllPatients() throws BlockchainAppException;

	List<PatientDTO> getPatientsByHospitalId(Long hospitalId) throws BlockchainAppException;

	void addIllnessRecord(String id, IllnessRecordDTO illnessRecordDTO) throws BlockchainAppException;
}
