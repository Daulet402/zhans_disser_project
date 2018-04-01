package blockchain.medical_card.dto.exceptions;

public class BlockchainAppException extends Exception{

	public BlockchainAppException() {
	}

	public BlockchainAppException(String message) {
		super(message);
	}

	public BlockchainAppException(String message, Throwable cause) {
		super(message, cause);
	}

	public BlockchainAppException(Throwable cause) {
		super(cause);
	}

	public BlockchainAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
