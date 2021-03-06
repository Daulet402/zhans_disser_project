package blockchain.medical_card.services.fx;

import blockchain.medical_card.api.BlockChainService;
import blockchain.medical_card.api.dao.PatientDaoService;
import blockchain.medical_card.api.dao.RecordDao;
import blockchain.medical_card.api.fx.IllnessRecordService;
import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.dto.IllnessRecordBlock;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.exceptions.MandatoryParameterMissedException;
import blockchain.medical_card.services.UserSessionService;
import blockchain.medical_card.utils.AlgorithmUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IllnessRecordServiceImpl implements IllnessRecordService {

    @Autowired
    private PatientDaoService patientDaoService;

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private BlockChainService blockChainService;

    @Override
    public void addIllnessRecord(String patientId, String plan, String diagnosis, String complaints, String illnessHistory, String inspectionType) throws BlockChainAppException {
        if (StringUtils.isEmpty(patientId))
            throw new MandatoryParameterMissedException("patientId is missed");

        DoctorDTO doctor = userSessionService.getDoctor();

        IllnessRecordDTO illnessRecordDTO = new IllnessRecordDTO();
        illnessRecordDTO.setPlan(plan);
        illnessRecordDTO.setDiagnosis(diagnosis);
        illnessRecordDTO.setComplaints(complaints);
        illnessRecordDTO.setVisitTime(LocalDateTime.now()); // TODO: 04/09/2018 now or input ?
        illnessRecordDTO.setIllnessHistory(illnessHistory);
        illnessRecordDTO.setInspectionType(inspectionType);
        illnessRecordDTO.setId(AlgorithmUtils.getUniqKey());
        illnessRecordDTO.setPatientId(patientId);
        if (doctor != null)
            illnessRecordDTO.setDoctorId(doctor.getId());

        //patientDaoService.addIllnessRecord(patientId, illnessRecordDTO); // TODO: 05/30/2018 read illness records of patients from block chain
        blockChainService.addRecords(Arrays.asList(illnessRecordDTO));
    }

    @Override
    public List<IllnessRecordDTO> getIllnessRecordsByPatientId(String patientId) {
        List<IllnessRecordDTO> records = new ArrayList<>();
        LinkedList<IllnessRecordBlock> blocks = blockChainService.getBlocks();
        if (CollectionUtils.isNotEmpty(blocks)) {
            for (IllnessRecordBlock block : blocks) {
                if (CollectionUtils.isNotEmpty(block.getIllnessRecords())) {
                    records.addAll(block.getIllnessRecords()
                            .stream()
                            .filter(recordDTO -> StringUtils.equals(patientId, recordDTO.getPatientId()))
                            .collect(Collectors.toList()));
                }
            }
        }
        return records;
    }
}
