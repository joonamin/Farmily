package com.ssafy.farmily.filter;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.farmily.dto.oauth.AuthenticatedUser;
import com.ssafy.farmily.exception.BusinessException;
import com.ssafy.farmily.repository.TokenBlackListRepository;
import com.ssafy.farmily.utils.JwtUtils;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final Key secretKey;
	private final TokenBlackListRepository tokenBlackListRepository;

	public JwtAuthenticationFilter(Environment env, TokenBlackListRepository tokenBlackListRepository) {
		String secret = env.getProperty("jwt.secret");
		log.warn("secret: " + secret);
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
		this.tokenBlackListRepository = tokenBlackListRepository;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String[] excludePath = {"/login", "/oauth2", "/swagger-ui", "/swagger-resources", "/v3/api-docs",
			"/webjars", "/error", "/favicon.ico", "/csrf", "/h2-console", "/test"};
		String path = request.getRequestURI();
		return Arrays.stream(excludePath).anyMatch(path::startsWith);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String resolveToken = JwtUtils.resolveToken(request);
		AuthenticatedUser authenticatedUser = JwtUtils.verifyToken(resolveToken, secretKey);

		tokenBlackListRepository.findByToken(resolveToken).ifPresent(e -> {
			throw new BusinessException(e.getToken().substring(7) + "...은 이미 로그아웃된 토큰 입니다");
		});

		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		filterChain.doFilter(request, response);
	}
}
