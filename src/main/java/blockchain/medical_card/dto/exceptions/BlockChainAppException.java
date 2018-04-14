package blockchain.medical_card.dto.exceptions;

public class BlockChainAppException extends Exception{

	public BlockChainAppException() {
	}

	public BlockChainAppException(String message) {
		super(message);
	}

	public BlockChainAppException(String message, Throwable cause) {
		super(message, cause);
	}

	public BlockChainAppException(Throwable cause) {
		super(cause);
	}

	public BlockChainAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
