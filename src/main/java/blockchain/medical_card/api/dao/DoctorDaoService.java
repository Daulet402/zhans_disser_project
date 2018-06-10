package blockchain.medical_card.api.dao;

import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;

import java.util.List;

public interface DoctorDaoService {

	void addDoctor(DoctorDTO doctor) throws BlockChainAppException;

	DoctorDTO getDoctorById(String id) throws BlockChainAppException;

	DoctorDTO getDoctorByUsername(String username) throws BlockChainAppException;

	List<DoctorDTO> getAllDoctors() throws BlockChainAppException;
}
