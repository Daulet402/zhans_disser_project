package blockchain.medical_card.mappers;

import blockchain.medical_card.dto.AddressDTO;
import blockchain.medical_card.dto.PatientDTO;
import blockchain.medical_card.utils.JsonUtils;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Document mapPatientDTO(PatientDTO patientDTO) {
        if (patientDTO == null)
            throw new IllegalArgumentException("Invalid input parameter");

        Document document = new Document();
        document.put("iin", patientDTO.getIin());
        document.put("height", patientDTO.getHeight());
        document.put("weight", patientDTO.getWeight());
        document.put("lastName", patientDTO.getLastName());
        document.put("bloodType", patientDTO.getBloodType());
        document.put("workPlace", patientDTO.getWorkPlace());
        document.put("firstName", patientDTO.getFirstName());
        document.put("middleName", patientDTO.getMiddleName());
        document.put("hospitalId", patientDTO.getHospitalId());
        document.put("phoneNumber", patientDTO.getPhoneNumber());
        document.put("address", JsonUtils.toJson(patientDTO.getAddress()));
        return document;
    }

    public PatientDTO mapDocument(Document document) {
        if (document == null)
            throw new IllegalArgumentException("Invalid input parameter");

        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setIin(document.getString("iin"));
        patientDTO.setHeight(document.getDouble("height"));
        patientDTO.setWeight(document.getDouble("weight"));
        patientDTO.setLastName(document.getString("lastName"));
        patientDTO.setFirstName(document.getString("firstName"));
        patientDTO.setHospitalId(document.getLong("hospitalId"));
        patientDTO.setBloodType(document.getString("bloodType"));
        patientDTO.setWorkPlace(document.getString("workPlace"));
        patientDTO.setId(document.getObjectId("_id").toString());
        patientDTO.setMiddleName(document.getString("middleName"));
        patientDTO.setPhoneNumber(document.getString("phoneNumber"));
        patientDTO.setAddress(JsonUtils.fromJson(document.getString("address"), AddressDTO.class));
        return patientDTO;
    }
}
