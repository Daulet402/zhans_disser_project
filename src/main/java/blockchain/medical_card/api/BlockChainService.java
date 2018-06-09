package blockchain.medical_card.api;

import blockchain.medical_card.dto.CheckResultDTO;
import blockchain.medical_card.dto.IllnessRecordBlock;
import blockchain.medical_card.dto.IllnessRecordDTO;

import java.util.LinkedList;
import java.util.List;

public interface BlockChainService {

    IllnessRecordBlock addRecords(List<IllnessRecordDTO> records);

    LinkedList<IllnessRecordBlock> getBlocks();

    CheckResultDTO checkBlock(IllnessRecordBlock block);

    void addNewBlock(IllnessRecordBlock block);
}
