package blockchain.medical_card.controllers;

import blockchain.medical_card.api.BlockChainService;
import blockchain.medical_card.api.CommunicationService;
import blockchain.medical_card.dto.IllnessRecordBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;

@RestController
public class MedicalBlockChainController {

	// TODO: 03/14/2018 Make it secure

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BlockChainService blockChainService;

	@Autowired
	private CommunicationService communicationService;


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
		IllnessRecordBlock block = new IllnessRecordBlock(Instant.now().getEpochSecond(), new ArrayList<>(), blockChainService.getBlocks().getLast().getPreviousHash());
		communicationService.notifyAllNodes(block);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.put("my-header", Arrays.asList("Some value", "Some value2", "Some value3", "value4"));

		LinkedHashMap<String, Object> body = new LinkedHashMap();
		body.put("int", 32145);
		body.put("pi", 3.14);
		body.put("str", "This is string");
		body.put("block", new IllnessRecordBlock());

		ResponseEntity<LinkedHashMap> responseEntity = new ResponseEntity<>(body, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(value = "/blocks/all", method = RequestMethod.GET)
	public LinkedList<IllnessRecordBlock> getAllBlocks() {
		return blockChainService.getBlocks();
	}

	@RequestMapping(value = "/blocks/new", method = RequestMethod.POST)
	public void addNewBlock(@RequestBody IllnessRecordBlock block, @RequestHeader(value = "from-host", required = false) String from) {
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("!!!!   New block  from  " + from);
		System.out.println(String.format("hash = %s, prev hash = %s, nonce = %s", block.getHash(), block.getPreviousHash(), block.getNonce()));
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
}
