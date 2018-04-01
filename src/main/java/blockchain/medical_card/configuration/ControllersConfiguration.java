package blockchain.medical_card.configuration;

import blockchain.medical_card.api.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@Configuration
public class ControllersConfiguration {

	@Autowired
	private ResourceLoader resourceLoader;

	@Bean(name = "login")
	public ViewHolder loginView() throws IOException {
		return loadView("classpath:/scenes/login_scene.fxml", "Вход");
	}

	@Bean(name = "registration")
	public ViewHolder registrationView() throws IOException {
		return loadView("classpath:/scenes/registration_scene.fxml", "Регистрация");
	}

	@Bean(name = "patient_register")
	public ViewHolder patientRegistrationView() throws IOException {
		return loadView("classpath:/scenes/patient_register_scene.fxml", "Регистрация пациента");
	}

	@Bean(name = "illness_record")
	public ViewHolder illnessRecordView() throws IOException {
		return loadView("classpath:/scenes/illness_record_scene.fxml", "Добавить запись");
	}

	@Bean(name = "patients")
	public ViewHolder patientsView() throws IOException {
		return loadView("classpath:/scenes/patients_scene.fxml", "Пациенты");
	}

	@Bean
	public Controller patientRegistrationController() throws IOException {
		return (Controller) patientRegistrationView().getController();
	}

	@Bean
	public Controller loginController() throws IOException {
		return (Controller) loginView().getController();
	}

	@Bean
	public Controller registrationController() throws IOException {
		return (Controller) registrationView().getController();
	}

	@Bean
	public Controller illnessRecordController() throws IOException {
		return (Controller) illnessRecordView().getController();
	}

	@Bean
	public Controller patientsController() throws IOException {
		return (Controller) patientsView().getController();
	}

	protected ViewHolder loadView(String url, String name) throws IOException {
		FXMLLoader loader = new FXMLLoader(resourceLoader.getResource(url).getURL());
		loader.load();
		return new ViewHolder(loader.getRoot(), loader.getController(), name);
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class ViewHolder {
		private Parent view;
		private Object controller;
		private String name;
	}
}
