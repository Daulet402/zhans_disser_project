package blockchain.medical_card.dto.mapper;

import blockchain.medical_card.dto.HostDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HostDTOMapper implements RowMapper {

    @Override
    public HostDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        HostDTO hostDTO = new HostDTO();
        hostDTO.setAddress(rs.getString("ip"));
        hostDTO.setPort(rs.getInt("port"));
        return hostDTO;
    }
}
