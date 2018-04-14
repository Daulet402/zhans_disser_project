package blockchain.medical_card.api;


import blockchain.medical_card.dto.exceptions.BlockChainAppException;

public interface FileService {

	void writeToFile(String fileName, String json) throws BlockChainAppException;

	String readFromFile(String fileName) throws BlockChainAppException;
}
