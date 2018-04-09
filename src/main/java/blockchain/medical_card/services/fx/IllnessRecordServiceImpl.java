package blockchain.medical_card.services.fx;

import blockchain.medical_card.api.dao.PatientDaoService;
import blockchain.medical_card.api.fx.IllnessRecordService;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.exceptions.BlockchainAppException;
import blockchain.medical_card.dto.exceptions.MandataryParameterMissedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IllnessRecordServiceImpl implements IllnessRecordService {

	@Autowired
	private PatientDaoService patientDaoService;

	@Override
	public void addIllnessRecord(String patientId, String plan, String diagnosis, String complaints, String illnessHistory, String inspectionType) throws BlockchainAppException {
		if (StringUtils.isEmpty(patientId))
			throw new MandataryParameterMissedException("patientId is missed");

		IllnessRecordDTO illnessRecordDTO = new IllnessRecordDTO();
		illnessRecordDTO.setPlan(plan);
		illnessRecordDTO.setDiagnosis(diagnosis);
		illnessRecordDTO.setComplaints(complaints);
		illnessRecordDTO.setIllnessHistory(illnessHistory);
		illnessRecordDTO.setInspectionType(inspectionType);

		patientDaoService.addIllnessRecord(patientId, illnessRecordDTO);
	}
}
