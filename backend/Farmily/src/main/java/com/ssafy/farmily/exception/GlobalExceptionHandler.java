package com.ssafy.farmily.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(NoSuchContentException.class)
	public ResponseEntity<String> handleException(NoSuchContentException exception) {
		String message = exception.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Void> handleException(MethodArgumentNotValidException exception) {
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<String> handleException(ForbiddenException exception) {
		String message = exception.getMessage();
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<String> handleException(BusinessException exception) {
		String message = exception.getMessage();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	@ExceptionHandler(InvalidJwtClaimException.class)
	public ResponseEntity<String> handleException(InvalidJwtClaimException exception) {
		String message = exception.getMessage();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
	}

	@ExceptionHandler(JwtNotFoundException.class)
	public ResponseEntity<String> handleException(JwtNotFoundException exception) {
		String message = exception.getMessage();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<String> handleException(ExpiredJwtException exception) {
		String message = exception.getMessage();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
	}
}
