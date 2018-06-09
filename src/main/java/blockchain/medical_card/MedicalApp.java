package blockchain.medical_card;

import blockchain.medical_card.configuration.ControllersConfiguration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		scanBasePackages = {"blockchain"}
)
public class MedicalApp extends AbstractJavaFxApplicationSupport {


	@Qualifier("login")
	@Autowired
	private ControllersConfiguration.ViewHolder loginViewHolder;

	@Qualifier("patient_register")
	@Autowired
	private ControllersConfiguration.ViewHolder patientRegisterViewHolder;

	@Qualifier("registration")
	@Autowired
	private ControllersConfiguration.ViewHolder registrationViewHolder;

	@Qualifier("illness_record")
	@Autowired
	private ControllersConfiguration.ViewHolder illnessRecordViewHolder;

	@Qualifier("patients")
	@Autowired
	private ControllersConfiguration.ViewHolder patientsViewHolder;

	@Override
	public void start(Stage primaryStage) throws Exception {
		startSceneByName(loginViewHolder.getName(), loginViewHolder.getView());
	}

	public void startSceneByName(String sceneName, Parent parent) throws Exception {
		Stage stage = new Stage();
		stage.setTitle(sceneName);
		stage.setResizable(true);
		stage.centerOnScreen();
		stage.setScene(new Scene(parent, 1500, 800));
		stage.show();
	}

	public static void main(String[] args) {
		launchApp(MedicalApp.class, args);
	}
}
