package blockchain.medical_card.dto.exceptions;

public class FileException extends BlockchainAppException {
	public FileException() {
	}

	public FileException(String message) {
		super(message);
	}

	public FileException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileException(Throwable cause) {
		super(cause);
	}
}
