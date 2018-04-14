package blockchain.medical_card.api.fx;

import blockchain.medical_card.dto.exceptions.BlockChainAppException;

public interface IllnessRecordService {

	void addIllnessRecord(String patientId, String plan, String diagnosis, String complaints, String illnessHistory, String inspectionType) throws BlockChainAppException;
}
