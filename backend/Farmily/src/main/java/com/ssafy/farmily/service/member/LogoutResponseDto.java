package com.ssafy.farmily.service.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LogoutResponseDto {

	private final String username;
	private final String expiredToken;

	@Builder
	public LogoutResponseDto(String username, String expiredToken) {
		this.username = username;
		this.expiredToken = expiredToken;
	}
}
