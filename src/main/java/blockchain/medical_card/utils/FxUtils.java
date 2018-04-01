package blockchain.medical_card.utils;

import blockchain.medical_card.configuration.ControllersConfiguration;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;

public class FxUtils {

	public static void setScene(ActionEvent actionEvent, ControllersConfiguration.ViewHolder viewHolder) {
		if (actionEvent != null && viewHolder != null) {
			Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
			currentStage.setTitle(viewHolder.getName());
			currentStage.getScene().setRoot(viewHolder.getView());
		}
	}

	public static void clean(TextInputControl... inputControls) {
		for (TextInputControl inputControl : inputControls)
			if (inputControl != null)
				inputControl.setText("");
	}

	public static Alert getAlertWindow(String title, String header, String content, Alert.AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}
}
