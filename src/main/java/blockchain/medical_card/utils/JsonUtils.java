package blockchain.medical_card.utils;

import com.google.gson.Gson;

public class JsonUtils {
	private static Gson gson = new Gson();

	public static String toJson(Object o) {
		return gson.toJson(o);
	}

	public static <T> T fromJson(String o, Class<T> clazz) {
		return gson.fromJson(o, clazz);
	}

	public static Gson getGson() {
		return gson;
	}
}
