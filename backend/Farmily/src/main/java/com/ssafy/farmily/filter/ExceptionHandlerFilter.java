package com.ssafy.farmily.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.farmily.exception.InvalidJwtClaimException;
import com.ssafy.farmily.exception.JwtNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(
		HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
	) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (InvalidJwtClaimException ex) {
			log.debug("잘못된 JWT Claim 예외 발생");
			sendErrorResponse(HttpStatus.UNAUTHORIZED, response, ex);
		} catch (JwtNotFoundException ex) {
			log.debug("JWT 없음 예외 발생");
			sendErrorResponse(HttpStatus.UNAUTHORIZED, response, ex);
		} catch (ExpiredJwtException ex) {
			log.debug("JWT 만료 예외 발생");
			sendErrorResponse(HttpStatus.UNAUTHORIZED, response, ex);
		} catch (RuntimeException ex){
			log.error("알 수 없는 런타임 예외 발생");
			sendErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, ex);
		}
	}

	public void sendErrorResponse(HttpStatus status, HttpServletResponse response,Throwable ex){
		response.setStatus(status.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	}
}