package blockchain.medical_card.utils;

import java.util.UUID;

public class CommonUtils {

	public static String getRandomString() {
		return UUID.randomUUID().toString().substring(0, 8);
	}
}
