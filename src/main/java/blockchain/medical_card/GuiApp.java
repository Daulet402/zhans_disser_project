package blockchain.medical_card;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GuiApp extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		startSceneByName(new Stage(), "/scenes/patients_scene.fxml");
		startSceneByName(new Stage(), "/scenes/patient_register_scene.fxml");
	}

	public void startSceneByName(Stage stage, String sceneName) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource(sceneName));
		stage.setTitle(sceneName);
		stage.setScene(new Scene(root, 1500, 800));
		stage.show();
	}
}
