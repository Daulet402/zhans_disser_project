package blockchain.medical_card.controllers;

import blockchain.medical_card.api.BlockChainService;
import blockchain.medical_card.api.CommunicationService;
import blockchain.medical_card.dto.IllnessRecordBlock;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.ResultDTO;
import blockchain.medical_card.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

@RestController
public class MedicalBlockChainController {

	// TODO: 03/14/2018 Make it secure

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BlockChainService blockChainService;

	@Autowired
	private CommunicationService communicationService;


	private List<IllnessRecordDTO> getRecordsList() {
		IllnessRecordDTO illnessRecordDTO = new IllnessRecordDTO();
		illnessRecordDTO.setComplaints("ddwfdfsad");
		illnessRecordDTO.setDoctorId("12342");
		illnessRecordDTO.setPlan("bjhnm");
		return Arrays.asList(illnessRecordDTO, new IllnessRecordDTO(), new IllnessRecordDTO(), new IllnessRecordDTO(), new IllnessRecordDTO());
	}

	@RequestMapping(value = "/test", produces = "application/json")
	public ResponseEntity<LinkedHashMap> readAllBlocks() {
		/*//restTemplate.getMessageConverters();
		ResponseEntity<IllnessRecordBlock[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/blocks/all", IllnessRecordBlock[].class);
		StringBuilder builder = new StringBuilder();
		Arrays.asList(responseEntity.getBody()).stream().forEach(block -> {
			builder.append(block);
			builder.append("\n");
		});
		return builder.toString();*/
		/*IllnessRecordBlock block = new IllnessRecordBlock(Instant.now().getEpochSecond(), new ArrayList<>(), blockChainService.getBlocks().getLast().getHash());
		block.mineBlock("0000");
		List<ResultDTO> resultDTOs = communicationService.notifyAllNodesToCheck(block);*/

		blockChainService.addRecords(getRecordsList());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.put("my-header", Arrays.asList("Some value", "Some value2", "Some value3", "value4"));

		LinkedHashMap<String, Object> body = new LinkedHashMap();
		body.put("int", 32145);
		body.put("pi", 3.14);
		body.put("str", "This is string");
		//body.put("result", resultDTOs);

		ResponseEntity<LinkedHashMap> responseEntity = new ResponseEntity<>(body, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(value = "/blocks/all", method = RequestMethod.GET)
	public LinkedList<IllnessRecordBlock> getAllBlocks() {
		return blockChainService.getBlocks();
	}

	@RequestMapping(value = Constants.ADD_BLOCK_URI, method = RequestMethod.POST)
	public ResultDTO addNewBlock(@RequestBody IllnessRecordBlock block, @RequestHeader(value = Constants.FROM_HEADER_NAME, required = false) String from) {
		System.out.println("request to add block from " + from);
		return blockChainService.addNewBlock(block);
	}

	@RequestMapping(value = Constants.CHECK_BLOCK_URI, method = RequestMethod.POST)
	public ResultDTO isNewBlockValid(@RequestBody IllnessRecordBlock block, @RequestHeader(value = Constants.FROM_HEADER_NAME, required = false) String from) {
		System.out.println("request to check block from " + from);
		return blockChainService.checkBlock(block);
	}
}
