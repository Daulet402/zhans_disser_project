package blockchain.medical_card.fx.controllers;

import blockchain.medical_card.api.Controller;
import blockchain.medical_card.api.fx.IllnessRecordService;
import blockchain.medical_card.configuration.ControllersConfiguration;
import blockchain.medical_card.configuration.PropertiesConfig;
import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.dto.PatientDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.info.InspectionType;
import blockchain.medical_card.services.UserSessionService;
import blockchain.medical_card.utils.FxUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;
import java.util.stream.Collectors;

public class IllnessRecordController implements Controller {

	@Qualifier("patients")
	@Autowired
	private ControllersConfiguration.ViewHolder patientsViewHolder;

	@Autowired
	private PropertiesConfig propertiesConfig;

	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private IllnessRecordService illnessRecordService;

	@FXML
	private TextArea complaints;

	@FXML
	private TextArea illnessHistory;

	@FXML
	private TextArea diagnosis;

	@FXML
	private TextArea plan;

	@FXML
	private ComboBox<String> inspectionType;

	@FXML
	private Label patientFio;

	@FXML
	private Label doctorFio;

	private PatientDTO patientDTO;

	@FXML
	protected void initialize() {
		inspectionType.setItems(FXCollections.observableList(
				Arrays.asList(InspectionType.values())
						.stream()
						.map(InspectionType::getName)
						.collect(Collectors.toList())));
	}

	public void back(ActionEvent actionEvent) {
		FxUtils.clean(complaints, illnessHistory, diagnosis, plan);
		inspectionType.setValue(null);
		FxUtils.setScene(actionEvent, patientsViewHolder);
		try {
			((PatientsController) patientsViewHolder.getController()).initAllData();
		} catch (BlockChainAppException e) {
			e.printStackTrace();
		}
	}

	public void addIllnessRecord(ActionEvent actionEvent) {
		if (patientDTO == null || StringUtils.isEmpty(patientDTO.getId())) {
			Alert alert = FxUtils.getAlertWindow(
					propertiesConfig.getErrorMessage(),
					null,
					propertiesConfig.getPatientNotFoundMessage(),
					Alert.AlertType.ERROR);
			alert.showAndWait();
			return;
		}
		try {
			illnessRecordService.addIllnessRecord(
					patientDTO.getId(),
					plan.getText(),
					diagnosis.getText(),
					complaints.getText(),
					illnessHistory.getText(),
					inspectionType.getValue());

			Alert alert = FxUtils.getAlertWindow(null, null, propertiesConfig.getSavedMessage(), Alert.AlertType.INFORMATION);
			alert.showAndWait();
			back(actionEvent);
		} catch (BlockChainAppException e) {
			e.printStackTrace();
			Alert alert = FxUtils.getAlertWindow(
					propertiesConfig.getErrorMessage(),
					null,
					propertiesConfig.getNotSavedMessage(),
					Alert.AlertType.ERROR);
			alert.showAndWait();
		}
	}

	public void setPatientDTO(PatientDTO patientDTO) {
		if (patientDTO != null)
			patientFio.setText(patientDTO.getFullName());
		this.patientDTO = patientDTO;
		setDoctorFio();
	}

	public void setDoctorFio() {
		DoctorDTO doctor = userSessionService.getDoctor();
		if (doctor != null) {
			doctorFio.setText(doctor.getFullName());
		}
	}
}
