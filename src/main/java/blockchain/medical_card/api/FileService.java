package blockchain.medical_card.api;


import blockchain.medical_card.dto.exceptions.BlockchainAppException;

import java.io.IOException;

public interface FileService {

	void writeToFile(String fileName, String json) throws BlockchainAppException;

	String readFromFile(String fileName) throws BlockchainAppException;
}
