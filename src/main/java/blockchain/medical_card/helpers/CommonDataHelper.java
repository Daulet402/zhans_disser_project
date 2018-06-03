package blockchain.medical_card.helpers;

import blockchain.medical_card.api.dao.CommonDaoService;
import blockchain.medical_card.api.dao.DoctorDaoService;
import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.dto.HospitalDTO;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.gui.TableItemDTO;
import blockchain.medical_card.dto.info.CityDTO;
import blockchain.medical_card.dto.info.DistrictDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CommonDataHelper {

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
				DoctorDTO doctor = doctorDaoService.getDoctorById(record.getDoctorId());
				if (doctor != null) {
					tableItemDTO.setDoctorFio(doctor.getFullName());
					List<CityDTO> cities = commonDaoService.getAllCities();

					if (CollectionUtils.isNotEmpty(cities)) {
						c:
						for (CityDTO c : cities) {
							if (CollectionUtils.isNotEmpty(c.getDistrictDTOList())) {
								for (DistrictDTO d : c.getDistrictDTOList()) {
									if (CollectionUtils.isNotEmpty(d.getHospitalDTOList())) {
										HospitalDTO hospital = d.getHospitalDTOList().stream()
												.filter(h -> Objects.equals(h.getHospitalId(), doctor.getHospitalId()))
												.findFirst()
												.orElse(null);
										if (hospital != null) {
											tableItemDTO.setHospitalName(String.format("%s (%s)", hospital.getName(), hospital.getAddress()));
											break c;
										}
									}
								}
							}
						}
					}
				}
			} catch (BlockChainAppException e) {
				e.printStackTrace();
			}
			return tableItemDTO;
		};

		return recordList
				.stream()
				.map(tableItemFunction)
				.collect(Collectors.toList());
	}
}
