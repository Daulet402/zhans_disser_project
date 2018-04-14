package blockchain.medical_card.mappers;

import blockchain.medical_card.api.dao.CommonDaoService;
import blockchain.medical_card.api.dao.DoctorDaoService;
import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.dto.HospitalDTO;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.gui.TableItemDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CommonDataMapper {

	@Autowired
	private DoctorDaoService doctorDaoService;

	@Autowired
	private CommonDaoService commonDaoService;

	public List<TableItemDTO> mapTableItem(List<IllnessRecordDTO> recordList) {
		if (CollectionUtils.isEmpty(recordList))
			return Collections.emptyList();

		Function<IllnessRecordDTO, TableItemDTO> tableItemFunction = record -> {
			TableItemDTO tableItemDTO = new TableItemDTO();
			tableItemDTO.setId(record.getId());
			tableItemDTO.setComplaint(record.getComplaints());
			tableItemDTO.setInspectionType(record.getInspectionType());
			tableItemDTO.setVisitTime(record.getVisitTime() != null ? record.getVisitTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:ss")) : "");
			try {
				DoctorDTO doctorDTO = doctorDaoService.getDoctorById(record.getDoctorId());
				if (doctorDTO != null) {
					tableItemDTO.setDoctorFio(doctorDTO.getFullName());
					HospitalDTO hospitalDTO = commonDaoService.getHospitalById(doctorDTO.getHospitalId());
					if (hospitalDTO != null)
						tableItemDTO.setHospitalName(hospitalDTO.getName());
				}
			} catch (BlockChainAppException e) {
				e.printStackTrace();
			}
			return tableItemDTO;
		};

		return recordList.stream().map(tableItemFunction).collect(Collectors.toList());
	}
}
