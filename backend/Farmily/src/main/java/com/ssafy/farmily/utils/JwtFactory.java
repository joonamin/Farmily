package com.ssafy.farmily.utils;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.nimbusds.jose.util.Base64;
import com.ssafy.farmily.dto.oauth.LoginResponseDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtFactory {

	private final Key key;
	public static final long ACCESS_TOKEN_EXPIRE_TIME_IN_MS = 1000 * 60 * 60 * 5; // 5시간
	public static final long REFRESH_TOKEN_EXPIRE_TIME_IN_MS = 1000 * 60 * 60 * 5; // 추후에 고도화할 예정입니다.

	protected static final String ISSUER = "farmily";


	public JwtFactory(@Value("${jwt.secret}") String secret) {
		Assert.notNull(secret, "설정 파일에 jwt secret이 존재하지 않음");
		log.warn("[JwtFactory] secret: " + secret);
		String base64EncodedSecret = Base64.encode(secret).decodeToString();
		this.key = Keys.hmacShaKeyFor(base64EncodedSecret.getBytes());
	}

	public LoginResponseDto createToken(OidcUser oidcUser) {
		String accessToken = createAccessToken(oidcUser);
		String refreshToken = createRefreshToken(oidcUser);
		return new LoginResponseDto(accessToken, refreshToken);
	}

	private String getUsername(OidcUser oidcUser) {
		return (String)oidcUser.getUserInfo().getClaims().get("sub");
	}

	private String createAccessToken(OidcUser oidcUser) {
		String username = getUsername(oidcUser);
		long currentTimeMillis = System.currentTimeMillis();
		return Jwts.builder()
			.subject(username)
			.expiration(new Date(currentTimeMillis + ACCESS_TOKEN_EXPIRE_TIME_IN_MS))
			.issuedAt(new Date(currentTimeMillis))
			.issuer(ISSUER)
			.claim("roles", "USER")
			.signWith(key)
			.compact();
	}

	private String createRefreshToken(OidcUser oidcUser) {
		String username = getUsername(oidcUser);
		long currentTimeMillis = System.currentTimeMillis();
		return Jwts.builder()
			.subject(username)
			.expiration(new Date(currentTimeMillis + REFRESH_TOKEN_EXPIRE_TIME_IN_MS))
			.issuedAt(new Date(currentTimeMillis))
			.issuer(ISSUER)
			.claim("roles", "USER")
			.signWith(key)
			.compact();
	}

}
