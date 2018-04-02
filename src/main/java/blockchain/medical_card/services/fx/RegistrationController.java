package blockchain.medical_card.services.fx;

import blockchain.medical_card.api.CityDaoService;
import blockchain.medical_card.api.FileService;
import blockchain.medical_card.configuration.ControllersConfiguration;
import blockchain.medical_card.configuration.PropertiesConfig;
import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.dto.exceptions.BlockchainAppException;
import blockchain.medical_card.dto.info.CityDTO;
import blockchain.medical_card.utils.AlgorithmUtils;
import blockchain.medical_card.utils.CommonUtils;
import blockchain.medical_card.utils.FxUtils;
import blockchain.medical_card.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RegistrationController extends AbstractRegistrationController {

	@Qualifier("login")
	@Autowired
	private ControllersConfiguration.ViewHolder loginViewHolder;

	@Autowired
	private CityDaoService cityDaoService;

	@Autowired
	private PropertiesConfig propertiesConfig;

	@Autowired
	private FileService fileService;

	@FXML
	private TextField lastName;

	@FXML
	private TextField firstName;

	@FXML
	private TextField middleName;

	@FXML
	private TextField iin;

	@FXML
	private TextField address;

	@FXML
	private TextField username;

	@FXML
	private TextField email;

	@Override
	protected List<CityDTO> getAllCities() {
		return cityDaoService.getAllCities();
	}

	public void register(ActionEvent actionEvent) throws BlockchainAppException {
		DoctorDTO doctorDTO = new DoctorDTO();
		doctorDTO.setFirstName(firstName.getText());
		doctorDTO.setLastName(lastName.getText());
		doctorDTO.setMiddleName(middleName.getText());
		doctorDTO.setIin(iin.getText());
		doctorDTO.setUsername(username.getText());
		doctorDTO.setEmail(email.getText());
		doctorDTO.setHospitalId(hospital.getValue() != null ? hospital.getValue().getHospitalId() : null);
		String pass = CommonUtils.getRandomString();
		doctorDTO.setPasswordHash(AlgorithmUtils.applySha256(pass));
		doctorDTO.setPassword(pass);

		String fileName = propertiesConfig
				.getFilesLocation()
				.concat(File.separator)
				.concat(propertiesConfig.getDoctorsFileName());

		List<DoctorDTO> doctorDTOs = JsonUtils
				.getGson()
				.fromJson(fileService.readFromFile(fileName), new TypeToken<List<DoctorDTO>>() {
				}.getType());

		if (CollectionUtils.isEmpty(doctorDTOs))
			doctorDTOs = new ArrayList<>();

		boolean doctorExists = doctorDTOs.stream().anyMatch(d ->
				StringUtils.equalsIgnoreCase(d.getFirstName(), doctorDTO.getFirstName())
						&& StringUtils.equalsIgnoreCase(d.getLastName(), doctorDTO.getLastName())
						&& StringUtils.equalsIgnoreCase(d.getMiddleName(), doctorDTO.getMiddleName())
						&& StringUtils.equalsIgnoreCase(d.getIin(), doctorDTO.getIin())
						&& StringUtils.equals(d.getIin(), doctorDTO.getIin()));

		if (doctorExists) {
			Alert alert = FxUtils.getAlertWindow(
					propertiesConfig.getErrorMessage(),
					null,
					propertiesConfig.getExistsMessage(),
					Alert.AlertType.ERROR);
			alert.showAndWait();
			return;
		}

		doctorDTOs.add(doctorDTO);
		fileService.writeToFile(fileName, JsonUtils.toJson(doctorDTOs));
		setSignInScene(actionEvent);
	}

	public void setSignInScene(ActionEvent actionEvent) {
		FxUtils.setScene(actionEvent, loginViewHolder);
		cleanFields();
	}

	public void cleanFields() {
		super.cleanFields();
		FxUtils.clean(
				firstName,
				lastName,
				middleName,
				iin,
				address,
				username,
				email
		);
	}
}
