package blockchain.medical_card.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:app.data.properties")
@PropertySource(value = "classpath:messages.yml", encoding = "UTF-8")
@Data
public class PropertiesConfig {

	@Value("${app.data.files.location}")
	private String filesLocation;

	@Value("${app.data.files.doctors.file}")
	private String doctorsFileName;

	@Value("${app.data.files.patients.file}")
	private String patientsFileName;

	//@Value("${error}")
	private String errorMessage = "Ошибка";

	//@Value("${doctor.exists}")
	private String existsMessage = "Запись уже существует";
	private String incorrectInputMessage = "Проверьте входные данные";

	//@Value("${doctor.exists}")
	private String doctorNotFoundMessage = "Не верные данные";

	private String loggedInUsernameTextPattern = "Вы зашли под именем %s";

	private String patientNotFoundMessage = "Пациент не найден";

	private String notSavedMessage = "Данные не сохранены";

	private String savedMessage = "Данные сохранены";
}
