package blockchain.medical_card.services.fx;

import blockchain.medical_card.api.CityDaoService;
import blockchain.medical_card.api.FileService;
import blockchain.medical_card.configuration.ControllersConfiguration;
import blockchain.medical_card.configuration.PropertiesConfig;
import blockchain.medical_card.dto.AddressDTO;
import blockchain.medical_card.dto.PatientDTO;
import blockchain.medical_card.dto.exceptions.BlockchainAppException;
import blockchain.medical_card.dto.info.CityDTO;
import blockchain.medical_card.utils.FxUtils;
import blockchain.medical_card.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
public class PatientRegistrationController extends AbstractRegistrationController {

	@Qualifier("patients")
	@Autowired
	private ControllersConfiguration.ViewHolder patientsViewHolder;

	@Autowired
	private CityDaoService cityDaoService;

	@Autowired
	private PropertiesConfig propertiesConfig;

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
		return cityDaoService.getAllCities();
	}

	public void registerPatient(ActionEvent actionEvent) throws BlockchainAppException {
		PatientDTO patientDTO = new PatientDTO();
		patientDTO.setFirstName(patientFirstName.getText());
		patientDTO.setLastName(patientLastName.getText());
		patientDTO.setMiddleName(patientMiddleName.getText());
		patientDTO.setIin(patientIIN.getText());
		patientDTO.setBloodType(patientBloodType.getText());
		patientDTO.setPhoneNumber(patientPhoneNumber.getText());
		patientDTO.setWorkPlace(workPlace.getText());
		patientDTO.setHospitalId(hospital.getValue() != null ? hospital.getValue().getHospitalId() : null);
		patientDTO.setAddress(new AddressDTO(
				city.getValue() != null ? city.getValue().getCityId() : null,
				district.getValue() != null ? district.getValue().getDistrictId() : null,
				patientAddress.getText()));

		try {
			patientDTO.setHeight(Double.parseDouble(patientHeight.getText()));
			patientDTO.setWeight(Double.parseDouble(patientWeight.getText()));
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

		String fileName = propertiesConfig
				.getFilesLocation()
				.concat(File.separator)
				.concat(propertiesConfig.getPatientsFileName());

		List<PatientDTO> patientDTOs = JsonUtils
				.getGson()
				.fromJson(fileService.readFromFile(fileName), new TypeToken<List<PatientDTO>>() {
				}.getType());

		if (CollectionUtils.isEmpty(patientDTOs))
			patientDTOs = new ArrayList<>();

		boolean patientExists = patientDTOs.stream().anyMatch(p ->
				StringUtils.equalsIgnoreCase(p.getFirstName(), patientDTO.getFirstName())
						&& StringUtils.equalsIgnoreCase(p.getLastName(), patientDTO.getLastName())
						&& StringUtils.equalsIgnoreCase(p.getMiddleName(), patientDTO.getMiddleName())
						&& StringUtils.equalsIgnoreCase(p.getIin(), patientDTO.getIin())
						&& StringUtils.equals(p.getIin(), patientDTO.getIin()));

		if (patientExists) {
			Alert alert = FxUtils.getAlertWindow(
					propertiesConfig.getErrorMessage(),
					null,
					propertiesConfig.getExistsMessage(),
					Alert.AlertType.ERROR);
			alert.showAndWait();
			return;
		}

		patientDTOs.add(patientDTO);
		fileService.writeToFile(fileName, JsonUtils.toJson(patientDTOs));
		back(actionEvent);
	}

	public void back(ActionEvent actionEvent) {
		FxUtils.setScene(actionEvent, patientsViewHolder);
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
