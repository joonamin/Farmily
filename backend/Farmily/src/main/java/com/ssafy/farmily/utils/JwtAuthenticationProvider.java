package com.ssafy.farmily.utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.farmily.dto.oauth.JwtAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author : 강민준(joonamin44@gmail.com)
 * @Date : 2024. 1. 26
 * @Desc : JWT 토큰을 검증하는 Provider
 * <p>1. authentication에서 authenticate() 시 사용하는 list에 추가해야한다.</p>
 * <p>2. authenticationManager에 등록해야한다.</p>
 */
@Component
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final byte[] keyBytes;

	public JwtAuthenticationProvider(@Value("${jwt.secret}") String secret) {
		this.keyBytes = secret.getBytes();
	}

	private Collection<? extends GrantedAuthority> createAuthorities(Claims claims) {
		List<String> roles = claims.get("roles", List.class);
		return roles.stream()
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());
	}
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Claims claims;

		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
		claims = Jwts.parser()
			.verifyWith(Keys.hmacShaKeyFor(keyBytes))
			.build()
			.parseSignedClaims(jwtAuthenticationToken.getJsonWebToken()).getPayload();

		log.info("authenticate 이전, claims : {}", claims);
		JwtAuthenticationToken result = new JwtAuthenticationToken(createAuthorities(claims),
			claims.getSubject(), null);
		log.info("authenticate 이후, result : {}", result);
		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
