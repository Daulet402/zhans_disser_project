package blockchain.medical_card.services.dao;

import blockchain.medical_card.api.dao.CommonDaoService;
import blockchain.medical_card.dto.HostDTO;
import blockchain.medical_card.dto.info.CityDTO;
import blockchain.medical_card.dto.mapper.CityResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CommonDaoServiceImpl implements CommonDaoService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override // TODO: 04/15/2018 make cachable
	public List<CityDTO> getAllCities() {
		return (List<CityDTO>) jdbcTemplate.query(
				"select c.city_id as city_id, c.name as city_name, c.code, d.district_id, d.name as district_name, h.hospital_id, h.name as hospital_name, h.address " +
						"from city c, district d, hospital h " +
						"where h.district_id=d.district_id and d.city_id=c.city_id",
				new CityResultSetExtractor());
	}

	@Override
	public List<HostDTO> getOtherHosts() {
		return Arrays.asList(
				getHost("localhost", 7070),
				getHost("localhost", 7071),
				/*getHost("localhost", 7072),
				getHost("localhost", 7073),
				getHost("localhost", 7074),*/
				getHost("localhost", 7075));
	}

	private HostDTO getHost(String address, int port) {
		HostDTO host = new HostDTO();
		host.setAddress(address);
		host.setPort(port);
		return host;
	}
}
