package blockchain.medical_card.services.dao;

import blockchain.medical_card.api.dao.PatientDaoService;
import blockchain.medical_card.dto.PatientDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.exceptions.BlockChainCodeException;
import blockchain.medical_card.dto.exceptions.MandatoryParameterMissedException;
import blockchain.medical_card.mappers.PatientMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PatientDaoServiceImpl implements PatientDaoService {

	public static final String PATIENTS_COLLECTION_NAME = "patients";

	@Autowired
	private MongoDatabase mongoDatabase;

	@Autowired
	private PatientMapper patientMapper;

	@Override
	public void addPatient(PatientDTO patient) throws BlockChainAppException {
		if (patient == null)
			throw new MandatoryParameterMissedException("Patient is required");

		PatientDTO patientByPersonalInfo = findPatientByPersonalInfo(patient.getFirstName(), patient.getLastName(), patient.getIin());
		if (patientByPersonalInfo != null)
			throw BlockChainCodeException.ofPatientAlreadyExist("This patient already exists");

		getCollection().insertOne(patientMapper.mapPatientDTO(patient));
	}

	@Override
	public List<PatientDTO> getAllPatients() throws BlockChainAppException {
		List<PatientDTO> patients = new ArrayList<>();
		FindIterable<Document> documents = getCollection().find();
		for (Document document : documents)
			patients.add(patientMapper.mapDocument(document));

		return patients;
	}

	@Override
	public List<PatientDTO> getPatientsByHospitalId(Long hospitalId) throws BlockChainAppException {
		List<PatientDTO> patients = new ArrayList<>();
		FindIterable<Document> documents = getCollection()
				.find(new BsonDocument("hospitalId", new BsonInt64(hospitalId)));

		for (Document document : documents)
			patients.add(patientMapper.mapDocument(document));

		return patients;
	}

	@Override
	public PatientDTO findPatientByPersonalInfo(String firstName, String lastName, String iin) throws BlockChainAppException {
		BsonElement firstNameElement = new BsonElement("firstName", new BsonString(firstName));
		BsonElement lastNameElement = new BsonElement("lastName", new BsonString(lastName));
		BsonElement iinElement = new BsonElement("iin", new BsonString(iin));

		Document document = getCollection()
				.find(new BsonDocument(Arrays.asList(firstNameElement, lastNameElement, iinElement)))
				.first();

		return document != null ? patientMapper.mapDocument(document) : null;
	}

	private MongoCollection<Document> getCollection() {
		return mongoDatabase.getCollection(PATIENTS_COLLECTION_NAME);
	}
}
