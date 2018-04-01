package blockchain.medical_card.services.dao;

import blockchain.medical_card.api.CityDaoService;
import blockchain.medical_card.dto.info.CityDTO;
import blockchain.medical_card.dto.mapper.CityResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CityDaoServiceImpl implements CityDaoService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<CityDTO> getAllCities() {
		return (List<CityDTO>) jdbcTemplate.query(
				"select c.city_id as city_id, c.name as city_name, c.code, d.district_id, d.name as district_name, h.hospital_id, h.name as hospital_name, h.address " +
						"from city c, district d, hospital h " +
						"where h.district_id=d.district_id and d.city_id=c.city_id",
				new CityResultSetExtractor());
	}
}
