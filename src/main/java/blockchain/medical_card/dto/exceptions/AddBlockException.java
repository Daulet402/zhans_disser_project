package blockchain.medical_card.dto.exceptions;

public class AddBlockException extends BlockChainAppException {
	public AddBlockException() {
		super();
	}

	public AddBlockException(String message) {
		super(message);
	}

	public AddBlockException(String message, Throwable cause) {
		super(message, cause);
	}
}
