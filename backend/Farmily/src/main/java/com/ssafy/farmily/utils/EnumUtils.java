package com.ssafy.farmily.utils;

public class EnumUtils {
	public static <E extends Enum<E>> E fromCamelCase(Class<E> type, String camelCase) {
		StringBuilder stringBuilder = new StringBuilder();
		char[] charArray = camelCase.toCharArray();
		for (char c: charArray) {
			if (Character.isUpperCase(c)) {
				stringBuilder.append('_').append(c);
			}
			else {
				stringBuilder.append(Character.toUpperCase(c));
			}
		}
		String upperSnakeCase = stringBuilder.toString();

		return Enum.valueOf(type, upperSnakeCase);
	}
}
