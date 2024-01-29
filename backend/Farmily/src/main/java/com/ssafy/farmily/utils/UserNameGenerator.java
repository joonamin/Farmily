package com.ssafy.farmily.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNameGenerator {
	public static String of(String providerName, String id) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(providerName);
		stringBuilder.append("_");
		stringBuilder.append(id);
		return stringBuilder.toString();
	}

	private static String extractUsername(String email) {
		return email.split("@")[0];
	}
}
