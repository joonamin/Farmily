package com.ssafy.farmily.dto.oauth;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.ToString;

// 인증을 위해 `AuthenticationManager`에게 전달될 토큰
@Getter
@ToString
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private String jsonWebToken;
	private Object principal;
	private Object credentials;

	public JwtAuthenticationToken(String jsonWebToken) {
		super(null);
		this.jsonWebToken = jsonWebToken;
		this.setAuthenticated(false); // JWT가 인증 받기 전 상태임을 표현
	}

	public JwtAuthenticationToken(
		Collection<? extends GrantedAuthority> authorities,
		Object principal, Object credentials) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true); // JWT가 인증 받은 상태임을 표현
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

}
