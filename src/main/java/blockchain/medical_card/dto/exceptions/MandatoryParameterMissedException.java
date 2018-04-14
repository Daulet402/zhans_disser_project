package blockchain.medical_card.dto.exceptions;

public class MandatoryParameterMissedException extends BlockChainAppException {
	public MandatoryParameterMissedException() {
	}

	public MandatoryParameterMissedException(String message) {
		super(message);
	}

	public MandatoryParameterMissedException(String message, Throwable cause) {
		super(message, cause);
	}
}
