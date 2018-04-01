package blockchain.medical_card.controllers;

import blockchain.medical_card.block.Block;
import blockchain.medical_card.block.BlockChain;
import blockchain.medical_card.dto.BlockDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class MedicalBlockChainController {

	// TODO: 03/14/2018 Make it secure

	private List<Block> blocks = new ArrayList<>();

	@Autowired
	private RestTemplate restTemplate;

	@PostConstruct
	public void init() {
		Block block1 = new Block(System.currentTimeMillis(), null, "0");
		Block block2 = new Block(System.currentTimeMillis(), null, block1.getHash());
		Block block3 = new Block(System.currentTimeMillis(), null, block2.getHash());

		blocks.add(block1);
		blocks.add(block2);
		blocks.add(block3);
	}

	@RequestMapping(value = "/test")
	public String test() {
		restTemplate.getMessageConverters();
		ResponseEntity<Block[]> responseEntity = restTemplate.getForEntity("http://localhost:7111/blocks/all", Block[].class);
		StringBuilder builder = new StringBuilder();
		Arrays.asList(responseEntity.getBody()).stream().forEach(block -> {
			builder.append(block);
			builder.append("\n");
		});
		return builder.toString();
	}

	@RequestMapping(value = "/blocks/all", method = RequestMethod.GET)
	public List<Block> getAllBlocks() {
		return blocks;
	}

	@RequestMapping(value = "/blocks/new", method = RequestMethod.POST)
	public void addNewBlock(@RequestBody BlockDTO blockDTO) {
		Block block = Block.builder()
				.previousHash(blockDTO.getPreviousHash())
				.timestamp(blockDTO.getTimestamp())
				.build();
		blocks.add(block);
	}
}
