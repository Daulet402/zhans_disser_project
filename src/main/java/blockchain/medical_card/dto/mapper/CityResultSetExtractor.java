package blockchain.medical_card.dto.mapper;

import blockchain.medical_card.dto.HospitalDTO;
import blockchain.medical_card.dto.info.CityDTO;
import blockchain.medical_card.dto.info.DistrictDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityResultSetExtractor implements ResultSetExtractor {

	@Override
	public List<CityDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<Long, CityDTO> cityDTOMap = new HashMap<>();
		Map<Long, DistrictDTO> districtDTOMap = new HashMap<>();

		while (rs.next()) {
			Long cityId = rs.getLong("city_id");
			CityDTO cityDTO = cityDTOMap.get(cityId);
			if (cityDTO == null) {
				cityDTO = new CityDTO(cityId, rs.getString("city_name"), rs.getString("code"), new ArrayList<>());
				cityDTOMap.put(cityId, cityDTO);
			}

			Long districtId = rs.getLong("district_id");
			DistrictDTO districtDTO = districtDTOMap.get(districtId);
			if (districtDTO == null) {
				districtDTO = new DistrictDTO(districtId, rs.getString("district_name"), cityId, new ArrayList<>());
				districtDTOMap.put(districtId, districtDTO);
				cityDTO.getDistrictDTOList().add(districtDTO);
			}

			HospitalDTO hospitalDTO = new HospitalDTO(rs.getLong("hospital_id"), rs.getString("hospital_name"), districtId, rs.getString("address"));
			districtDTO.getHospitalDTOList().add(hospitalDTO);
		}

		return new ArrayList(cityDTOMap.values());
	}
}
