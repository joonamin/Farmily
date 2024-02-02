package com.ssafy.farmily.utils;

import java.security.Key;

import javax.crypto.SecretKey;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ssafy.farmily.dto.oauth.AuthenticatedUser;
import com.ssafy.farmily.exception.InvalidJwtClaimException;
import com.ssafy.farmily.exception.JwtNotFoundException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtils {

	private JwtUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
			log.info("bearer token is null");
			throw new JwtNotFoundException("인증이 필요한 API에 필요한 token이 없습니다.");
		}
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public static String getUsernameFromToken(String token, Key key) {
		Assert.notNull(token, "token must not be null");
		Assert.notNull(key, "key must not be null");
		return Jwts.parser()
			.verifyWith((SecretKey)key)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getSubject();
	}

	public static AuthenticatedUser verifyToken(String token, Key key) throws ExpiredJwtException {
		if (token == null) {
			log.info("token is null");
			throw new JwtNotFoundException("토큰이 존재하지 않습니다.");
		}
		try {
			Claims claims = Jwts.parser()
				.verifyWith((SecretKey)key)
				.build().parseSignedClaims(token).getPayload();
			if (!claims.getIssuer().equals(JwtFactory.ISSUER)) {
				throw new InvalidJwtClaimException("Invalid Issuer");
			}
			if (!claims.containsKey("roles")) {
				throw new InvalidJwtClaimException("Invalid Roles");
			}
			if (!claims.containsKey("sub")) {
				throw new InvalidJwtClaimException("Invalid Subject");
			}
			return new AuthenticatedUser(claims.getSubject());
		} catch (ExpiredJwtException e) {
			log.error("ExpiredJwtException: {}", e.getMessage());
			throw e;
		}
	}

}
