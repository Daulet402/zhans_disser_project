package blockchain.medical_card.api.fx;

import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;

public interface LoginService {

	DoctorDTO getDoctorByUsernameAndPasswordHash(String username, String passwordHash) throws BlockChainAppException;
}
