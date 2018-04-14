package blockchain.medical_card.fx.controllers;

import blockchain.medical_card.api.FileService;
import blockchain.medical_card.api.fx.PatientRegistrationService;
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
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Data
public class PatientRegistrationController extends AbstractRegistrationController {

	@Qualifier("patients")
	@Autowired
	private ControllersConfiguration.ViewHolder patientsViewHolder;

	@Autowired
	private PropertiesConfig propertiesConfig;

	@Autowired
	private PatientRegistrationService patientRegistrationService;

	@Autowired
	private FileService fileService;

	@FXML
	private TextField patientFirstName;

	@FXML
	private TextField patientLastName;

	@FXML
	private TextField patientMiddleName;

	@FXML
	private TextField patientIIN;

	@FXML
	private TextField patientBloodType;

	@FXML
	private TextField patientHeight;

	@FXML
	private TextField patientWeight;

	@FXML
	private TextField patientAddress;

	@FXML
	private TextField patientPhoneNumber;

	@FXML
	private TextField workPlace;

	@Override
	protected List<CityDTO> getAllCities() {
		return patientRegistrationService.getAllCities();
	}

	public void registerPatient(ActionEvent actionEvent) throws BlockChainAppException {
		Double height = null;
		Double weight = null;

		try {
			height = Double.parseDouble(patientHeight.getText());
			weight = Double.parseDouble(patientWeight.getText());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Alert alert = FxUtils.getAlertWindow(
					propertiesConfig.getErrorMessage(),
					null,
					propertiesConfig.getIncorrectInputMessage(),
					Alert.AlertType.ERROR);
			alert.showAndWait();
			return;
		}

		try {
			patientRegistrationService.registerPatient(
					patientFirstName.getText(),
					patientLastName.getText(),
					patientMiddleName.getText(),
					patientIIN.getText(),
					patientBloodType.getText(),
					patientPhoneNumber.getText(),
					workPlace.getText(),
					hospital.getValue() != null ? hospital.getValue().getHospitalId() : null,
					city.getValue() != null ? city.getValue().getCityId() : null,
					district.getValue() != null ? district.getValue().getDistrictId() : null,
					patientAddress.getText(),
					height,
					weight);

			Alert alert = FxUtils.getAlertWindow(null, null, propertiesConfig.getSavedMessage(), Alert.AlertType.INFORMATION);
			alert.showAndWait();
			back(actionEvent);
		} catch (BlockChainCodeException e) {
			e.printStackTrace();
			if (e.getExceptionCode() == BlockChainCodeException.ExceptionCode.PATIENT_ALREADY_EXISTS) {
				Alert alert = FxUtils.getAlertWindow(
						propertiesConfig.getErrorMessage(),
						null,
						propertiesConfig.getExistsMessage(),
						Alert.AlertType.ERROR);
				alert.showAndWait();
			}
		} catch (BlockChainAppException e) {
			e.printStackTrace();
			Alert alert = FxUtils.getAlertWindow(
					propertiesConfig.getErrorMessage(),
					null,
					"",
					Alert.AlertType.ERROR);
			alert.showAndWait();
		}
	}

	public void back(ActionEvent actionEvent) {
		FxUtils.setScene(actionEvent, patientsViewHolder);
		try {
			((PatientsController) patientsViewHolder.getController()).initAllData();
		} catch (BlockChainAppException e) {
			e.printStackTrace();
		}
		cleanFields();
	}

	public void cleanFields() {
		super.cleanFields();
		FxUtils.clean(
				patientFirstName,
				patientLastName,
				patientMiddleName,
				patientIIN,
				patientAddress,
				patientBloodType,
				patientHeight,
				patientWeight,
				patientPhoneNumber,
				workPlace
		);
	}
}
