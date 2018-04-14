package blockchain.medical_card.fx.controllers;


import blockchain.medical_card.api.Controller;
import blockchain.medical_card.api.FileService;
import blockchain.medical_card.api.fx.LoginService;
import blockchain.medical_card.configuration.ControllersConfiguration;
import blockchain.medical_card.configuration.PropertiesConfig;
import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.services.UserSessionService;
import blockchain.medical_card.utils.AlgorithmUtils;
import blockchain.medical_card.utils.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Data
public class LoginController implements Controller {

	@Qualifier("registration")
	@Autowired
	private ControllersConfiguration.ViewHolder registrationViewHolder;

	@Qualifier("patients")
	@Autowired
	private ControllersConfiguration.ViewHolder patientsViewHolder;

	@Autowired
	private LoginService loginService;

	@Autowired
	private PropertiesConfig propertiesConfig;

	@Autowired
	UserSessionService userSessionService;

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	public void signIn(ActionEvent actionEvent) throws BlockChainAppException {
		String passwordHash = AlgorithmUtils.applySha256(password.getText());
		Alert alert = FxUtils.getAlertWindow(
				propertiesConfig.getErrorMessage(),
				null,
				propertiesConfig.getDoctorNotFoundMessage(),
				Alert.AlertType.ERROR);

		DoctorDTO doctor = loginService.getDoctorByUsernameAndPasswordHash(username.getText(), passwordHash);
		if (doctor != null) {
			userSessionService.setDoctor(doctor);
			PatientsController patientsController = (PatientsController) patientsViewHolder.getController();
			patientsController.getLoggedInUsername().setText(
					String.format(propertiesConfig.getLoggedInUsernameTextPattern(), doctor.getFullName()));
			FxUtils.setScene(actionEvent, patientsViewHolder);
			return;
		}
		alert.showAndWait();
	}

	public void register(ActionEvent actionEvent) throws Exception {
		FxUtils.setScene(actionEvent, registrationViewHolder);
	}
}
