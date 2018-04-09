package blockchain.medical_card.fx.controllers;

import blockchain.medical_card.api.Controller;
import blockchain.medical_card.api.dao.CityDaoService;
import blockchain.medical_card.api.dao.PatientDaoService;
import blockchain.medical_card.configuration.ControllersConfiguration;
import blockchain.medical_card.dto.HospitalDTO;
import blockchain.medical_card.dto.PatientDTO;
import blockchain.medical_card.dto.TreeItemDTO;
import blockchain.medical_card.dto.exceptions.BlockchainAppException;
import blockchain.medical_card.dto.info.CityDTO;
import blockchain.medical_card.dto.info.DistrictDTO;
import blockchain.medical_card.services.UserSessionService;
import blockchain.medical_card.utils.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;
import java.util.List;

@Data
public class PatientsController implements Controller {

	@Qualifier("patient_register")
	@Autowired
	private ControllersConfiguration.ViewHolder patientRegisterViewHolder;

	@Autowired
	UserSessionService userSessionService;

	@Qualifier("illness_record")
	@Autowired
	private ControllersConfiguration.ViewHolder illnessRecordViewHolder;

	@Autowired
	private PatientDaoService patientDaoService;

	@Autowired
	private CityDaoService cityDaoService;

	@FXML
	private Label workPlace;

	@FXML
	private Label bloodType;

	@FXML
	private TreeView<TreeItemDTO> patientsTree;

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
	private Button addRecordButton;

	@FXML
	private Button detailButton;

	private PatientDTO patientDTO;

	@FXML
	protected void initialize() {
	}

	@PostConstruct
	public void init() {
		initPatientsTreeView();
	}

	private void initPatientsTreeView() {
		TreeItem<TreeItemDTO> root = new TreeItem<>();
		root.setExpanded(true);

		List<CityDTO> cityList = cityDaoService.getAllCities();

		for (CityDTO cityDTO : cityList) {
			makePatientTreeFromCityDto(cityDTO, root);
		}
		patientsTree.setShowRoot(false);
		patientsTree.setRoot(root);

		patientsTree.getSelectionModel().selectedItemProperty().addListener((v, oldVal, newVal) -> {
			if (newVal != null)
				if (newVal.getValue().getObject() instanceof PatientDTO) {
					if (newVal.getValue() != null) {
						handlePatientSelectEventInternal(false, newVal.getValue().getName());
						setPatientDTO((PatientDTO) newVal.getValue().getObject());
					}
				} else {
					handlePatientSelectEventInternal(true, "");
				}
		});
	}

	private void handlePatientSelectEventInternal(boolean isDisable, String name) {
		addRecordButton.setDisable(isDisable);
		fio.setText(name);
	}

	private void handleRecordSelectEventInternal(boolean isDisable) {
		detailButton.setDisable(isDisable);
	}


	private void fillLabelsWithPatientInfo(PatientDTO patientDTO) {

	}

	private TreeItem<TreeItemDTO> makePatientTreeFromCityDto(CityDTO city, TreeItem<TreeItemDTO> parent) {
		TreeItem<TreeItemDTO> cityItem = new TreeItem<>(new TreeItemDTO(city, city.getName(), ""));
		for (DistrictDTO district : city.getDistrictDTOList()) {
			TreeItem<TreeItemDTO> districtItem = new TreeItem<>(new TreeItemDTO(district, district.getName(), city.getName()));

			for (HospitalDTO hospital : district.getHospitalDTOList()) {
				TreeItem<TreeItemDTO> hospitalItem = new TreeItem<>(new TreeItemDTO(hospital, hospital.getName(), district.getName()));

				try {
					List<PatientDTO> patientsList = patientDaoService.getPatientsByHospitalId(hospital.getHospitalId());
					if (CollectionUtils.isNotEmpty(patientsList))
						for (PatientDTO patientDTO : patientsList) {
							TreeItem<TreeItemDTO> patientItem = new TreeItem<>(new TreeItemDTO(
									patientDTO,
									String.format("%s %s %s", patientDTO.getLastName(), patientDTO.getFirstName(), patientDTO.getMiddleName()),
									hospital.getName()));

							hospitalItem.getChildren().add(patientItem);
						}

				} catch (BlockchainAppException e) {
					e.printStackTrace();
					return null;
				}
				districtItem.getChildren().add(hospitalItem);
			}
			cityItem.getChildren().add(districtItem);
		}
		parent.getChildren().add(cityItem);
		return cityItem;
	}

	public void addPatient(ActionEvent actionEvent) {
		FxUtils.setScene(actionEvent, patientRegisterViewHolder);
	}

	public void addRecord(ActionEvent actionEvent) {
		((IllnessRecordController) illnessRecordViewHolder.getController()).setPatientDTO(SerializationUtils.clone(getPatientDTO()));
		FxUtils.setScene(actionEvent, illnessRecordViewHolder);
	}

	public void seeInDetail(ActionEvent actionEvent) {

	}
}
