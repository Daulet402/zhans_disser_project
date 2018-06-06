package blockchain.medical_card.api.fx;

import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;

import java.util.List;

public interface IllnessRecordService {

	void addIllnessRecord(String patientId, String plan, String diagnosis, String complaints, String illnessHistory, String inspectionType) throws BlockChainAppException;

	List<IllnessRecordDTO> getIllnessRecordsByPatientId(String patientId);
}
