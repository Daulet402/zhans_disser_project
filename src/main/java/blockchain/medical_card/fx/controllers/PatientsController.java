package blockchain.medical_card.fx.controllers;

import blockchain.medical_card.api.Controller;
import blockchain.medical_card.api.fx.PatientsService;
import blockchain.medical_card.configuration.ControllersConfiguration;
import blockchain.medical_card.dto.AddressDTO;
import blockchain.medical_card.dto.HospitalDTO;
import blockchain.medical_card.dto.PatientDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.gui.TableItemDTO;
import blockchain.medical_card.dto.gui.TreeItemDTO;
import blockchain.medical_card.dto.info.CityDTO;
import blockchain.medical_card.dto.info.DistrictDTO;
import blockchain.medical_card.mappers.CommonDataMapper;
import blockchain.medical_card.services.UserSessionService;
import blockchain.medical_card.utils.FxUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private PatientsService patientsService;

	@Autowired
	private CommonDataMapper dataMapper;

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
	private TableView<TableItemDTO> recordsTable;

	@FXML
	private Label loggedInUsername;

	@FXML
	private Button addRecordButton;

	@FXML
	private Button detailButton;

	private PatientDTO patientDTO;

	@FXML
	private TableColumn<TableItemDTO, String> visitTime;

	@FXML
	private TableColumn<TableItemDTO, String> doctorFio;

	@FXML
	private TableColumn<TableItemDTO, String> hospitalName;

	@FXML
	private TableColumn<TableItemDTO, String> inspectionType;

	@FXML
	private TableColumn<TableItemDTO, String> complaint;

	@FXML
	protected void initialize() {
	}

	@PostConstruct
	public void init() throws BlockChainAppException {
		initAllData();
	}

	public void initAllData() throws BlockChainAppException {
		initPatientsTreeView();
		initRecordsTableView();
	}

	private void initPatientsTreeView() throws BlockChainAppException {
		TreeItem<TreeItemDTO> root = new TreeItem<>();
		root.setExpanded(true);

		List<CityDTO> cityList = patientsService.getAllCities();

		for (CityDTO cityDTO : cityList) {
			makePatientTreeFromCityDto(cityDTO, root);
		}
		patientsTree.setShowRoot(false);
		patientsTree.setRoot(root);

		patientsTree.getSelectionModel().selectedItemProperty().addListener((v, oldVal, newVal) -> {
			if (newVal != null)
				if (newVal.getValue().getObject() instanceof PatientDTO) {
					if (newVal.getValue() != null) {
						fillPatientInfo((PatientDTO) newVal.getValue().getObject());
						setPatientDTO((PatientDTO) newVal.getValue().getObject());
						addRecordButton.setDisable(false);
					}
				} else {
					addRecordButton.setDisable(true);
					//fillPatientInfo(null);
				}
		});
	}

	private void initRecordsTableView() {
		visitTime.setCellValueFactory(new PropertyValueFactory("visitTime"));
		doctorFio.setCellValueFactory(new PropertyValueFactory("doctorFio"));
		hospitalName.setCellValueFactory(new PropertyValueFactory("hospitalName"));
		inspectionType.setCellValueFactory(new PropertyValueFactory("inspectionType"));
		complaint.setCellValueFactory(new PropertyValueFactory("complaint"));
	}

	private void fillPatientInfo(PatientDTO patientDTO) {
		if (patientDTO != null) {
			AddressDTO addressDTO = patientDTO.getAddress();
			if (addressDTO != null)
				address.setText(addressDTO.getAddress());

			fio.setText(patientDTO.getFullName());
			iin.setText(patientDTO.getIin());
			phoneNumber.setText(patientDTO.getPhoneNumber());
			height.setText(String.valueOf(patientDTO.getHeight()));
			weight.setText(String.valueOf(patientDTO.getWeight()));
			bloodType.setText(patientDTO.getBloodType());
			workPlace.setText(patientDTO.getWorkPlace());
			recordsTable.setItems(FXCollections.observableArrayList(dataMapper.mapTableItem(patientDTO.getIllnessRecordList())));
		} else {
			fio.setText("");
			address.setText("");
			iin.setText("");
			phoneNumber.setText("");
			height.setText("");
			weight.setText("");
			bloodType.setText("");
			workPlace.setText("");
			recordsTable.setItems(FXCollections.emptyObservableList());
		}
	}

	private TreeItem<TreeItemDTO> makePatientTreeFromCityDto(CityDTO city, TreeItem<TreeItemDTO> parent) {
		TreeItem<TreeItemDTO> cityItem = new TreeItem<>(new TreeItemDTO(city, city.getName(), ""));
		for (DistrictDTO district : city.getDistrictDTOList()) {
			TreeItem<TreeItemDTO> districtItem = new TreeItem<>(new TreeItemDTO(district, district.getName(), city.getName()));

			for (HospitalDTO hospital : district.getHospitalDTOList()) {
				TreeItem<TreeItemDTO> hospitalItem = new TreeItem<>(new TreeItemDTO(hospital, hospital.getName(), district.getName()));

				try {
					List<PatientDTO> patientsList = patientsService.getPatientsByHospitalId(hospital.getHospitalId());
					if (CollectionUtils.isNotEmpty(patientsList))
						for (PatientDTO patientDTO : patientsList) {
							TreeItem<TreeItemDTO> patientItem = new TreeItem<>(new TreeItemDTO(
									patientDTO,
									patientDTO.getFullName(),
									hospital.getName()));

							hospitalItem.getChildren().add(patientItem);
						}

				} catch (BlockChainAppException e) {
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
