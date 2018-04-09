package blockchain.medical_card.api.dao;

import blockchain.medical_card.dto.info.CityDTO;

import java.util.List;

public interface CityDaoService {

	List<CityDTO> getAllCities();
}
