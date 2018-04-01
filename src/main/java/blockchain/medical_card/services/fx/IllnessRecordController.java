package blockchain.medical_card.services.fx;

import blockchain.medical_card.api.Controller;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.utils.FxUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class IllnessRecordController implements Controller {

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

	public void cancel(ActionEvent actionEvent) {
		FxUtils.clean(complaints, illnessHistory, diagnosis, plan);
	}

	public void addIllnessRecord(ActionEvent actionEvent) {
		IllnessRecordDTO illnessRecordDTO = new IllnessRecordDTO();

		illnessRecordDTO.setComplaints(complaints.getText());
		illnessRecordDTO.setIllnessHistory(illnessHistory.getText());
		illnessRecordDTO.setDiagnosis(diagnosis.getText());
		illnessRecordDTO.setPlan(plan.getText());

		System.out.println(illnessRecordDTO);
	}
}
