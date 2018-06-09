package blockchain.medical_card.api;

import blockchain.medical_card.dto.IllnessRecordBlock;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.ResultDTO;

import java.util.LinkedList;
import java.util.List;

public interface BlockChainService {

    IllnessRecordBlock addRecords(List<IllnessRecordDTO> records);

    LinkedList<IllnessRecordBlock> getBlocks();

    ResultDTO checkBlock(IllnessRecordBlock block);

    ResultDTO addNewBlock(IllnessRecordBlock block);
}
