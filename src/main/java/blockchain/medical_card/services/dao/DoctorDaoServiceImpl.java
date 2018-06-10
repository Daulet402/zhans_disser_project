package blockchain.medical_card.services.dao;

import blockchain.medical_card.api.dao.DoctorDaoService;
import blockchain.medical_card.dto.DoctorDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;
import blockchain.medical_card.dto.exceptions.BlockChainCodeException;
import blockchain.medical_card.dto.exceptions.MandatoryParameterMissedException;
import blockchain.medical_card.mappers.DoctorMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;


@Component
public class DoctorDaoServiceImpl implements DoctorDaoService {

	public static final String DOCTORS_COLLECTION_NAME = "doctors";

	@Autowired
	private MongoDatabase mongoDatabase;

	@Autowired
	private DoctorMapper doctorMapper;

	@Override
	public void addDoctor(DoctorDTO doctor) throws BlockChainAppException {
		if (doctor == null)
			throw new MandatoryParameterMissedException("Doctor is required");

		DoctorDTO doctorByUsername = getDoctorByUsername(doctor.getUsername());
		if (doctorByUsername != null)
			throw BlockChainCodeException.ofDoctorAlreadyExist("Doctor already exists");

		getCollection().insertOne(doctorMapper.mapDoctorDTO(doctor));
	}

	@Override
	public DoctorDTO getDoctorById(String id) throws BlockChainAppException {
		Document document = getCollection().find(eq("_id", new ObjectId(id))).first();
		if (document == null)
			throw BlockChainCodeException.ofDoctorNotFound(String.format("Doctor with id %s not found", id));

		return doctorMapper.mapDocument(document);
	}

	@Override
	public List<DoctorDTO> getAllDoctors() throws BlockChainAppException {
		List<DoctorDTO> doctors = new ArrayList<>();
		FindIterable<Document> documents = getCollection().find();
		for (Document document : documents)
			doctors.add(doctorMapper.mapDocument(document));

		return doctors;
	}

	@Override
	public DoctorDTO getDoctorByUsername(String username) throws BlockChainAppException {
		Document document = getCollection()
				.find(new BsonDocument("username", new BsonString(username)))
				.first();
		return document != null ? doctorMapper.mapDocument(document) : null;
	}

	private MongoCollection<Document> getCollection() {
		return mongoDatabase.getCollection(DOCTORS_COLLECTION_NAME);
	}
}
