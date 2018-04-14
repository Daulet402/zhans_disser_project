package blockchain.medical_card.services.fx;

import blockchain.medical_card.api.dao.DoctorDaoService;
import blockchain.medical_card.api.fx.LoginService;
import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.exceptions.MandatoryParameterMissedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private DoctorDaoService doctorDaoService;

	@Override
	public DoctorDTO getDoctorByUsernameAndPasswordHash(String username, String passwordHash) throws BlockChainAppException {
		if (StringUtils.isAnyEmpty(username, passwordHash))
			throw new MandatoryParameterMissedException("Username and password hash are required");

		DoctorDTO doctor = doctorDaoService.getDoctorByUsername(username);
		return doctor != null && StringUtils.equals(doctor.getPasswordHash(), passwordHash) ? doctor : null;
	}
}
