package blockchain.medical_card.fx.controllers;

import blockchain.medical_card.api.fx.RegistrationService;
import blockchain.medical_card.configuration.ControllersConfiguration;
import blockchain.medical_card.configuration.PropertiesConfig;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.exceptions.BlockChainCodeException;
import blockchain.medical_card.dto.info.CityDTO;
import blockchain.medical_card.utils.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class RegistrationController extends AbstractRegistrationController {

	@Qualifier("login")
	@Autowired
	private ControllersConfiguration.ViewHolder loginViewHolder;

	@Autowired
	private PropertiesConfig propertiesConfig;

	@Autowired
	private RegistrationService registrationService;

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
		return registrationService.getAllCities();
	}

	public void register(ActionEvent actionEvent) throws BlockChainAppException {
		try {
			registrationService.registerDoctor(
					firstName.getText(),
					lastName.getText(),
					middleName.getText(),
					iin.getText(),
					email.getText(),
					username.getText(),
					hospital.getValue() != null ? hospital.getValue().getHospitalId() : null);
		} catch (BlockChainCodeException e) {
			e.printStackTrace();
			if (e.getExceptionCode() == BlockChainCodeException.ExceptionCode.DOCTOR_ALREADY_EXISTS) {
				Alert alert = FxUtils.getAlertWindow(
						propertiesConfig.getErrorMessage(),
						null,
						propertiesConfig.getExistsMessage(),
						Alert.AlertType.ERROR);
				alert.showAndWait();
			}
			return;
		} catch (BlockChainAppException e) {
			e.printStackTrace();
			Alert alert = FxUtils.getAlertWindow(
					propertiesConfig.getErrorMessage(),
					null,
					"",
					Alert.AlertType.ERROR);
			alert.showAndWait();
			return;
		}
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
