package com.ssafy.farmily.entity;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.util.Assert;

import com.ssafy.farmily.dto.LogoutRequestDto;
import com.ssafy.farmily.utils.JwtFactory;

import jakarta.persistence.Id;
import lombok.Getter;

@RedisHash("REVOKED_TOKEN_")
@Getter
public class Token {

	@Id
	private Long id;

	@Indexed
	private String token;

	private String type;

	@TimeToLive
	private Long ttl;

	public Token(String token, String type) {
		this.token = token;
		this.type = type;
		if (type.equals("refresh"))
			this.ttl = JwtFactory.REFRESH_TOKEN_EXPIRE_TIME_IN_MS / 1000;
		else if (type.equals("access"))
			this.ttl = JwtFactory.ACCESS_TOKEN_EXPIRE_TIME_IN_MS / 1000;
	}

	public static Token from(LogoutRequestDto logoutRequestDto) {
		Assert.notNull(logoutRequestDto, "logoutRequestDto must not be null");
		Assert.notNull(logoutRequestDto.getToken(), "token must not be null");
		Assert.notNull(logoutRequestDto.getType(), "type must not be null");
		return new Token(logoutRequestDto.getToken(), logoutRequestDto.getType());
	}
}
