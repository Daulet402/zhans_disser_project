package blockchain.medical_card.services.fx;


import blockchain.medical_card.api.Controller;
import blockchain.medical_card.api.FileService;
import blockchain.medical_card.configuration.ControllersConfiguration;
import blockchain.medical_card.configuration.PropertiesConfig;
import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.dto.exceptions.BlockchainAppException;
import blockchain.medical_card.services.UserSessionService;
import blockchain.medical_card.utils.AlgorithmUtils;
import blockchain.medical_card.utils.FxUtils;
import blockchain.medical_card.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Data
public class LoginController implements Controller {

	@Qualifier("registration")
	@Autowired
	private ControllersConfiguration.ViewHolder registrationViewHolder;

	@Qualifier("patients")
	@Autowired
	private ControllersConfiguration.ViewHolder patientsViewHolder;

	@Autowired
	private FileService fileService;

	@Autowired
	private PropertiesConfig propertiesConfig;

	@Autowired
	UserSessionService userSessionService;

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	public void signIn(ActionEvent actionEvent) throws BlockchainAppException {
		String fileName = propertiesConfig
				.getFilesLocation()
				.concat(File.separator)
				.concat(propertiesConfig.getDoctorsFileName());

		String passwordHash = AlgorithmUtils.applySha256(password.getText());
		Alert alert = FxUtils.getAlertWindow(
				propertiesConfig.getErrorMessage(),
				null,
				propertiesConfig.getDoctorNotFoundMessage(),
				Alert.AlertType.ERROR);

		List<DoctorDTO> doctorDTOs = JsonUtils
				.getGson()
				.fromJson(fileService.readFromFile(fileName), new TypeToken<List<DoctorDTO>>() {
				}.getType());

		if (CollectionUtils.isNotEmpty(doctorDTOs)) {
			Optional<DoctorDTO> doctorOptional = doctorDTOs.stream()
					.filter(doctorDTO -> StringUtils.equalsIgnoreCase(doctorDTO.getUsername(), username.getText()))
					.filter(doctorDTO -> StringUtils.equalsIgnoreCase(doctorDTO.getPasswordHash(), passwordHash))
					.findFirst();

			if (doctorOptional.isPresent()) {
				DoctorDTO doctorDTO = doctorOptional.get();
				userSessionService.setDoctor(doctorDTO);
				PatientsController patientsController = (PatientsController) patientsViewHolder.getController();
				patientsController.getLoggedInUsername().setText(
						String.format(propertiesConfig.getLoggedInUsernameTextPattern(),
								doctorDTO.getLastName() + " " + doctorDTO.getFirstName()));
				FxUtils.setScene(actionEvent, patientsViewHolder);
				return;
			}

			alert.showAndWait();
			return;
		}
		alert.showAndWait();
	}

	public void register(ActionEvent actionEvent) throws Exception {
		FxUtils.setScene(actionEvent, registrationViewHolder);
	}
}
