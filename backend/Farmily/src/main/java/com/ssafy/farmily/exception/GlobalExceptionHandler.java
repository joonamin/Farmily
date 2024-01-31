package com.ssafy.farmily.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(NoSuchContentException.class)
	public ResponseEntity<String> handleException(NoSuchContentException exception) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Void> handleException(MethodArgumentNotValidException exception) {
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Void> handleException(BusinessException exception) {
		return ResponseEntity.badRequest().build();
	}
}
