package blockchain.medical_card.services.fx;

import blockchain.medical_card.api.Controller;
import blockchain.medical_card.dto.HospitalDTO;
import blockchain.medical_card.dto.info.CityDTO;
import blockchain.medical_card.dto.info.DistrictDTO;
import blockchain.medical_card.utils.CollectionUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import javax.annotation.PostConstruct;
import java.util.List;

public abstract class AbstractRegistrationController implements Controller {

	@FXML
	protected ComboBox<CityDTO> city;

	@FXML
	protected ComboBox<DistrictDTO> district;

	@FXML
	protected ComboBox<HospitalDTO> hospital;

	@FXML
	protected void initialize() {
		addListeners();
	}

	@PostConstruct
	public void init() {
		if (city != null)
			city.setItems(CollectionUtils.convertToObservableList(getAllCities()));
	}

	private void addListeners() {
		if (city != null)
			city.valueProperty().addListener((observable, oldValue, newValue) -> {
				if (district != null) {
					district.setItems(CollectionUtils.convertToObservableList(newValue != null ? newValue.getDistrictDTOList() : null));
					district.getSelectionModel().selectFirst();
				}
			});

		if (district != null)
			district.valueProperty().addListener((observable, oldValue, newValue) -> {
				if (hospital != null) {
					hospital.setItems(CollectionUtils.convertToObservableList(newValue != null ? newValue.getHospitalDTOList() : null));
					hospital.getSelectionModel().selectFirst();
				}
			});
	}

	protected abstract List<CityDTO> getAllCities();

	protected void cleanFields() {
		city.setValue(null);
		district.setValue(null);
		hospital.setValue(null);
	}

	protected void cleanComboBoxes() {
		city.setValue(null);
		district.setValue(null);
		hospital.setValue(null);
	}
}
