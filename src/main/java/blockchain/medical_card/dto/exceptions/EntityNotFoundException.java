package blockchain.medical_card.dto.exceptions;

public class EntityNotFoundException extends BlockchainAppException {

	public enum ExceptionType {
		PATIENT_NOT_FOUND,
		DOCTOR_NOT_FOUND,
	}

	public EntityNotFoundException() {
	}

	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityNotFoundException(ExceptionType exceptionType, String message) {
		super(String.format("%s: %s", exceptionType, message));
	}
}
