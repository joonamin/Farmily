package com.ssafy.farmily.dto.oauth;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.micrometer.common.util.StringUtils;
import lombok.ToString;

@ToString
public class AuthenticatedUser implements Authentication {
	// 실제 인증 정보를 담는 객체
	private String username;

	public AuthenticatedUser(String username) {
		this.username = username;
	}

	// 현재는 로그인 한 상태 / 하지 않은 상태만 구분합니다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(() -> "ROLE_USER");
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return username;
	}

	@Override
	public boolean isAuthenticated() {
		return !StringUtils.isEmpty(username);
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

	}

	@Override
	public String getName() {
		return username;
	}

}
