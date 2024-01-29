package com.ssafy.farmily.filter;

import java.io.IOException;
import java.security.Key;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.farmily.dto.oauth.JwtAuthenticationToken;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.ssafy.farmily.exception.InvalidJwtClaimException;
import com.ssafy.farmily.utils.JwtUtils;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final Environment env;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		// request로 들어온 authorization 필드를 검사한다.
		String token = JwtUtils.resolveToken(request);
		Key key = Keys.hmacShaKeyFor(env.getProperty("jwt.secret").getBytes());
		// 검증한다
		log.info("JwtAuthenticationFilter has been callled");
		// JwtUtils.verifyToken(token, key);

		// 검증이 끝나면 Authentication 객체를 만들어서 SecurityContext에 넣어준다.
		// lifecycle은 request 한번에 그친다 (SecurityContext는 ThreadLocal에 저장되어있다)
		log.info("doFilterInternal, token : {}", token);
		// new JwtAuthenticationToken(, , null)
		Authentication authentication = new JwtAuthenticationToken(token);
	}

}
