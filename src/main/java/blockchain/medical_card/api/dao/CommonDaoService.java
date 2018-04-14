package blockchain.medical_card.api.dao;

import blockchain.medical_card.dto.HospitalDTO;
import blockchain.medical_card.dto.info.CityDTO;

import java.util.List;

public interface CommonDaoService {

	List<CityDTO> getAllCities();

	HospitalDTO getHospitalById(Long id);
}
