package com.ssafy.farmily.utils;

import java.security.Key;

import javax.crypto.SecretKey;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ssafy.farmily.exception.InvalidJwtClaimException;

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
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public static void verifyToken(String token, Key key) throws ExpiredJwtException {
		Assert.notNull(token, "token cannot be null");
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
		} catch (ExpiredJwtException e) {
			log.error("ExpiredJwtException: {}", e.getMessage());
			throw e;
		}
	}

}
