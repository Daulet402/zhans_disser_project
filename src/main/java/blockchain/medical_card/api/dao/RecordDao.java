package blockchain.medical_card.api.dao;

import blockchain.medical_card.dto.IllnessRecordDTO;
import blockchain.medical_card.dto.exceptions.BlockChainAppException;

import java.util.List;

public interface RecordDao {

    List<IllnessRecordDTO> getTempRecords() throws BlockChainAppException;

    void addTempRecord(IllnessRecordDTO record) throws BlockChainAppException;

    void clearTempRecordList() throws BlockChainAppException;
}
