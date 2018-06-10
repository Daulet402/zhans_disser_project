package blockchain.medical_card.dto.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockChainCodeException extends BlockChainAppException {

	private ExceptionCode exceptionCode;

	public enum ExceptionCode {
		PATIENT_ALREADY_EXISTS,
		DOCTOR_ALREADY_EXISTS,
		PATIENT_NOT_FOUND,
		DOCTOR_NOT_FOUND,
	}

	public BlockChainCodeException(String message) {
		super(message);
	}

	public BlockChainCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public BlockChainCodeException(ExceptionCode exceptionCode, String message) {
		super(String.format("%s: %s", exceptionCode, message));
		this.exceptionCode = exceptionCode;
	}

	public static BlockChainCodeException ofPatientAlreadyExist(String message) {
		return new BlockChainCodeException(ExceptionCode.PATIENT_ALREADY_EXISTS, message);
	}

	public static BlockChainCodeException ofDoctorAlreadyExist(String message) {
		return new BlockChainCodeException(ExceptionCode.DOCTOR_ALREADY_EXISTS, message);
	}

	public static BlockChainCodeException ofPatientNotFound(String message) {
		return new BlockChainCodeException(ExceptionCode.PATIENT_NOT_FOUND, message);
	}

	public static BlockChainCodeException ofDoctorNotFound(String message) {
		return new BlockChainCodeException(ExceptionCode.DOCTOR_NOT_FOUND, message);
	}
}
