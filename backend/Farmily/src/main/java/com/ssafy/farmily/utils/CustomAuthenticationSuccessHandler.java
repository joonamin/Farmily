package com.ssafy.farmily.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ssafy.farmily.dto.oauth.LoginResponseDto;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtFactory jwtFactory;
	private final Environment env;
	private String REDIRECT_URL;
	@PostConstruct
	void init() {
		REDIRECT_URL = env.getProperty("spring.security.farmily.redirect-uri");
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		// 인증이 성공했으므로 jwt를 발급
		OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
		OidcUserInfo userInfo = oidcUser.getUserInfo();
		userInfo.getClaims().forEach((k, v) -> log.trace("[userInfo Claims] k: {}, v: {}", k, v));

		// client cookie에 jwt를 저장한다.
		// (xss 방지, cookie httpOnly 설정)
		// (csrf 방지, cookie secure 설정) - dev환경에서는 적용해제
		LoginResponseDto tokenResponse = jwtFactory.createToken(oidcUser);
		CookieUtils.addOAuth2TokenToBrowser(response, tokenResponse);
		log.info("onAuthenticationSuccess has been called in CustomAuthenticationSuccessHandler");
		response.sendRedirect(REDIRECT_URL);
	}
}
