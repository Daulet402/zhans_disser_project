package blockchain.medical_card.services.dao;

import blockchain.medical_card.api.FileService;
import blockchain.medical_card.api.dao.PatientDaoService;
import blockchain.medical_card.configuration.PropertiesConfig;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.PatientDTO;
import blockchain.medical_card.dto.exceptions.BlockChainCodeException;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.exceptions.MandatoryParameterMissedException;
import blockchain.medical_card.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientDaoServiceImpl implements PatientDaoService {

	@Autowired
	private FileService fileService;

	@Autowired
	private PropertiesConfig propertiesConfig;

	@Override
	public void addPatient(PatientDTO patient) throws BlockChainAppException {
		if (patient == null)
			throw new MandatoryParameterMissedException("Patient is required");

		List<PatientDTO> patientDTOs = getAllPatients();
		if (CollectionUtils.isEmpty(patientDTOs))
			patientDTOs = new ArrayList<>();
		else {
			boolean patientExists = patientDTOs.stream().anyMatch(p ->
					StringUtils.equalsIgnoreCase(p.getFirstName(), patient.getFirstName())
							&& StringUtils.equalsIgnoreCase(p.getLastName(), patient.getLastName())
							&& StringUtils.equalsIgnoreCase(p.getMiddleName(), patient.getMiddleName())
							&& StringUtils.equals(p.getIin(), patient.getIin()));

			if (patientExists)
				throw BlockChainCodeException.ofPatientAlreadyExist("This patient already exists");
		}
		patientDTOs.add(patient);
		fileService.writeToFile(getPatientsFileName(), JsonUtils.toJson(patientDTOs));
	}

	@Override
	public List<PatientDTO> getAllPatients() throws BlockChainAppException {
		return JsonUtils
				.getGson()
				.fromJson(fileService.readFromFile(getPatientsFileName()), new TypeToken<List<PatientDTO>>() {
				}.getType());

	}

	@Override
	public List<PatientDTO> getPatientsByHospitalId(Long hospitalId) throws BlockChainAppException {
		List<PatientDTO> patientDTOs = getAllPatients();
		return CollectionUtils.isEmpty(patientDTOs) || hospitalId == null ? Collections.emptyList() :
				patientDTOs
						.stream()
						.filter(patientDTO -> patientDTO.getHospitalId() != null)
						.filter(patientDTO -> Long.valueOf(hospitalId).compareTo(patientDTO.getHospitalId()) == 0)
						.collect(Collectors.toList());
	}

	@Override
	public void addIllnessRecord(String id, IllnessRecordDTO illnessRecordDTO) throws BlockChainAppException {
		List<PatientDTO> patientDTOs = getAllPatients();

		if (CollectionUtils.isEmpty(patientDTOs))
			throw BlockChainCodeException.ofPatientNotFound("Patient list is empty");


		PatientDTO patientDTO = patientDTOs
				.stream().filter(p -> StringUtils.equals(id, p.getId()))
				.findFirst()
				.orElseThrow(() -> BlockChainCodeException
						.ofPatientNotFound(String.format("Patient with id %s not found", id))
				);

		patientDTO.getIllnessRecordList().add(illnessRecordDTO);
		fileService.writeToFile(getPatientsFileName(), JsonUtils.toJson(patientDTOs));
	}

	private String getPatientsFileName() {
		return propertiesConfig
				.getFilesLocation()
				.concat(File.separator)
				.concat(propertiesConfig.getPatientsFileName());
	}
}
