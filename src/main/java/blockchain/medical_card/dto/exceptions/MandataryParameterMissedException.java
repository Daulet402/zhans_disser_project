package blockchain.medical_card.dto.exceptions;

public class MandataryParameterMissedException extends BlockchainAppException {
	public MandataryParameterMissedException() {
	}

	public MandataryParameterMissedException(String message) {
		super(message);
	}

	public MandataryParameterMissedException(String message, Throwable cause) {
		super(message, cause);
	}
}
