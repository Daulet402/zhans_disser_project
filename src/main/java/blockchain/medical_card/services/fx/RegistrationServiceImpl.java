package blockchain.medical_card.services.fx;

import blockchain.medical_card.api.EmailService;
import blockchain.medical_card.api.dao.CommonDaoService;
import blockchain.medical_card.api.dao.DoctorDaoService;
import blockchain.medical_card.api.fx.RegistrationService;
import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.info.CityDTO;
import blockchain.medical_card.utils.AlgorithmUtils;
import blockchain.medical_card.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:app.mail.properties")
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private DoctorDaoService doctorDaoService;

	@Autowired
	private CommonDaoService commonDaoService;

	@Autowired
	private EmailService emailService;

	@Value("${app.mail.username}")
	private String from;

	private static final String TITLE = "Регистрация";
	private static final String TEXT_PATTERN = "Уважаемый пользователь! Ваш пароль для входа в систему Medical Block Chain App  %s";

	@Override
	public List<CityDTO> getAllCities() {
		return commonDaoService.getAllCities();
	}

	@Override
	public void registerDoctor(
			String firstName,
			String lastName,
			String middleName,
			String iin,
			String email,
			String username,
			Long hospitalId) throws BlockChainAppException {

		String password = CommonUtils.getRandomString();

		DoctorDTO doctorDTO = new DoctorDTO();
		doctorDTO.setIin(iin);
		doctorDTO.setEmail(email);
		doctorDTO.setLastName(lastName);
		doctorDTO.setUsername(username);
		doctorDTO.setFirstName(firstName);
		doctorDTO.setMiddleName(middleName);
		doctorDTO.setHospitalId(hospitalId);
		doctorDTO.setPasswordHash(AlgorithmUtils.applySha256(password));

		doctorDaoService.addDoctor(doctorDTO);
		emailService.sendMail(doctorDTO.getEmail(), from, TITLE, String.format(TEXT_PATTERN, password));
		System.out.println(String.format("password %s was sent to mail %s", password, doctorDTO.getEmail()));
	}
}
