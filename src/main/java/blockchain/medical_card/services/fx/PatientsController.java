package blockchain.medical_card.services.fx;

import blockchain.medical_card.api.Controller;
import blockchain.medical_card.configuration.ControllersConfiguration;
import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.services.UserSessionService;
import blockchain.medical_card.utils.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Data
public class PatientsController implements Controller {

	@Qualifier("patient_register")
	@Autowired
	private ControllersConfiguration.ViewHolder patientRegisterViewHolder;

	@Autowired
	UserSessionService userSessionService;

	@FXML
	private Label workPlace;

	@FXML
	private Label bloodType;

	@FXML
	private TreeView patientsTree;

	@FXML
	private Label fio;

	@FXML
	private Label address;

	@FXML
	private Label iin;

	@FXML
	private Label phoneNumber;

	@FXML
	private Label height;

	@FXML
	private Label weight;

	@FXML
	private TableView recordsTable;

	@FXML
	private Label loggedInUsername;

	@FXML
	protected void initialize() {
		addListeners();
	}

	private void addListeners() {

	}

	public void addPatient(ActionEvent actionEvent) {
		FxUtils.setScene(actionEvent, patientRegisterViewHolder);
	}

	public void addRecord(ActionEvent actionEvent) {
	}
}
