package blockchain.medical_card.api.dao;

import blockchain.medical_card.dto.HostDTO;
import blockchain.medical_card.dto.info.CityDTO;

import java.util.List;

public interface CommonDaoService {

	List<CityDTO> getAllCities();

	List<HostDTO> getOtherHosts();
}
