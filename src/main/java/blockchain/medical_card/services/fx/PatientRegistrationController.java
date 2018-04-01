package blockchain.medical_card.services.fx;

import blockchain.medical_card.api.CityDaoService;
import blockchain.medical_card.configuration.ControllersConfiguration;
import blockchain.medical_card.dto.AddressDTO;
import blockchain.medical_card.dto.PatientDTO;
import blockchain.medical_card.dto.info.CityDTO;
import blockchain.medical_card.utils.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
	private CityDaoService cityDaoService;

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

	public void registerPatient(ActionEvent actionEvent) {
		PatientDTO patientDTO = new PatientDTO();
		patientDTO.setFirstName(patientFirstName.getText());
		patientDTO.setLastName(patientLastName.getText());
		patientDTO.setLastName(patientLastName.getText());
		patientDTO.setIin(patientIIN.getText());
		patientDTO.setBloodType(patientBloodType.getText());
		patientDTO.setHeight(patientHeight.getText());
		patientDTO.setWeight(patientWeight.getText());
		patientDTO.setPhoneNumber(patientPhoneNumber.getText());
		patientDTO.setWorkPlace(workPlace.getText());
		//patientDTO.setAddress(new AddressDTO(city.getValue(), district.getValue(), patientAddress.getText()));

		System.out.println(patientDTO);
	}

	public void back(ActionEvent actionEvent) {
		FxUtils.setScene(actionEvent, patientsViewHolder);
		cleanFields();
	}

	public void cleanFields() {
		super.cleanFields();
		FxUtils.clean(patientFirstName, patientLastName, patientMiddleName, patientIIN, patientAddress, patientBloodType, patientHeight, patientWeight, patientPhoneNumber, workPlace);
	}
}
