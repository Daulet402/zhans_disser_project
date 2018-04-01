package blockchain.medical_card.services;

import blockchain.medical_card.api.FileService;
import blockchain.medical_card.dto.exceptions.BlockchainAppException;
import blockchain.medical_card.dto.exceptions.FileException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FileServiceImpl implements FileService {

	@Override
	public void writeToFile(String fileName, String json) throws BlockchainAppException {
		if (StringUtils.isAnyBlank(fileName, json))
			throw new IllegalArgumentException("Mandatory parameter missed");

		try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
			outputStream.write(json.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileException(e);
		}
	}

	@Override
	public String readFromFile(String fileName) throws BlockchainAppException {
		if (StringUtils.isAnyBlank(fileName))
			throw new IllegalArgumentException("Mandatory parameter missed");

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
			return bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileException(e);
		}
	}
}
