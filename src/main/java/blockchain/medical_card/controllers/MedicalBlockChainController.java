package blockchain.medical_card.controllers;

import blockchain.medical_card.api.BlockChainService;
import blockchain.medical_card.dto.IllnessRecordBlock;
import blockchain.medical_card.dto.ResultDTO;
import blockchain.medical_card.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
public class MedicalBlockChainController {

	@Autowired
	private BlockChainService blockChainService;


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
