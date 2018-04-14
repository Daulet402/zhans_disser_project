package blockchain.medical_card;

import blockchain.medical_card.configuration.ControllersConfiguration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

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
		//startSceneByName(patientRegisterViewHolder.getName(), patientRegisterViewHolder.getView());
		//startSceneByName(illnessRecordViewHolder.getName(), illnessRecordViewHolder.getView());
		//startSceneByName(patientsViewHolder.getName(), patientsViewHolder.getView());
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
		launchApp(MedicalApp.class, args, 7373);
		//launchApp(MedicalApp.class, args, 7111);
		//launchApp(MedicalApp.class, args, 7112);
	}

	/*public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(MedicalApp.class, args);

		CityDaoService cityDaoService = applicationContext.getBean(CityDaoService.class);
		List<CityDTO> cityDTOs = cityDaoService.getAllCities();
		System.out.println(cityDTOs);
		//runOnPort(args, 7111);
		//runOnPort(args, 7112);
		GuiApp.main(args);
		//Controller controller = applicationContext.getBean(Controller.class);
		//runOnPort(args, 7113);
	}*/

	public static void runOnPort(String[] args, int port) {
		System.getProperties().put("server.port", port);
		ApplicationContext applicationContext = SpringApplication.run(MedicalApp.class, args);
	}
}
