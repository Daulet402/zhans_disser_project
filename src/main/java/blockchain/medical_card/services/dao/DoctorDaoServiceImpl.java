package blockchain.medical_card.services.dao;

import blockchain.medical_card.api.FileService;
import blockchain.medical_card.api.dao.DoctorDaoService;
import blockchain.medical_card.configuration.PropertiesConfig;
import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.exceptions.BlockChainCodeException;
import blockchain.medical_card.dto.exceptions.MandatoryParameterMissedException;
import blockchain.medical_card.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class DoctorDaoServiceImpl implements DoctorDaoService {

	@Autowired
	private FileService fileService;

	@Autowired
	private PropertiesConfig propertiesConfig;

	@Override
	public void addDoctor(DoctorDTO doctor) throws BlockChainAppException {
		if (doctor == null)
			throw new MandatoryParameterMissedException("Doctor is required");

		List<DoctorDTO> doctorDTOs = getAllDoctors();
		if (CollectionUtils.isEmpty(doctorDTOs)) {
			doctorDTOs = new ArrayList<>();
		} else {
			boolean doctorExists = doctorDTOs.stream().anyMatch(d ->
					StringUtils.equalsIgnoreCase(d.getFirstName(), doctor.getFirstName())
							&& StringUtils.equalsIgnoreCase(d.getLastName(), doctor.getLastName())
							&& StringUtils.equalsIgnoreCase(d.getMiddleName(), doctor.getMiddleName())
							&& StringUtils.equals(d.getIin(), doctor.getIin()));

			if (doctorExists)
				throw BlockChainCodeException.ofDoctorAlreadyExist("Doctor already exists");
		}
		doctorDTOs.add(doctor);
		fileService.writeToFile(getDoctorsFileName(), JsonUtils.toJson(doctorDTOs));
	}

	@Override
	public DoctorDTO getDoctorById(String id) throws BlockChainAppException {
		return null;
	}

	public List<DoctorDTO> getAllDoctors() throws BlockChainAppException {
		return JsonUtils
				.getGson()
				.fromJson(fileService.readFromFile(getDoctorsFileName()), new TypeToken<List<DoctorDTO>>() {
				}.getType());

	}

	@Override
	public DoctorDTO getDoctorByUsername(String username) throws BlockChainAppException {
		List<DoctorDTO> doctorDTOs = getAllDoctors();
		return CollectionUtils.isNotEmpty(doctorDTOs) ? doctorDTOs
				.stream()
				.filter(doctorDTO -> StringUtils.equals(doctorDTO.getUsername(), username))
				.findFirst()
				.orElse(null)
				: null;
	}

	private String getDoctorsFileName() {
		return propertiesConfig
				.getFilesLocation()
				.concat(File.separator)
				.concat(propertiesConfig.getDoctorsFileName());
	}
}
