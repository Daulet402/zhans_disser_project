package blockchain.medical_card.api.dao;

import blockchain.medical_card.dto.IllnessRecordBlock;
import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;

import java.util.List;

public interface BlockDao {

    void addRecords(List<IllnessRecordDTO> records) throws BlockChainAppException;

    List<IllnessRecordBlock> getBlocks() throws BlockChainAppException;
}
