package blockchain.medical_card.mappers;

import blockchain.medical_card.dto.AddressDTO;
import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.utils.JsonUtils;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public Document mapDoctorDTO(DoctorDTO doctorDTO) {
        if (doctorDTO == null)
            throw new IllegalArgumentException("Invalid input parameter");

        Document document = new Document();
        document.put("iin", doctorDTO.getIin());
        document.put("email", doctorDTO.getEmail());
        document.put("username", doctorDTO.getUsername());
        document.put("lastName", doctorDTO.getLastName());
        document.put("firstName", doctorDTO.getFirstName());
        document.put("middleName", doctorDTO.getMiddleName());
        document.put("hospitalId", doctorDTO.getHospitalId());
        document.put("passwordHash", doctorDTO.getPasswordHash());
        document.put("address", JsonUtils.toJson(doctorDTO.getAddress()));
        return document;
    }

    public DoctorDTO mapDocument(Document document) {
        if (document == null)
            throw new IllegalArgumentException("Invalid input parameter");

        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setId(document.getObjectId("_id").toString());
        doctorDTO.setIin(document.getString("iin"));
        doctorDTO.setEmail(document.getString("email"));
        doctorDTO.setUsername(document.getString("username"));
        doctorDTO.setLastName(document.getString("lastName"));
        doctorDTO.setFirstName(document.getString("firstName"));
        doctorDTO.setHospitalId(document.getLong("hospitalId"));
        doctorDTO.setMiddleName(document.getString("middleName"));
        doctorDTO.setPasswordHash(document.getString("passwordHash"));
        doctorDTO.setAddress(JsonUtils.fromJson(document.getString("address"), AddressDTO.class));
        return doctorDTO;
    }
}
