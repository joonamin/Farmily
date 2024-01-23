package com.ssafy.farmily.exception;

public class InvalidEmailRegistrationException extends RuntimeException {
	public InvalidEmailRegistrationException(String msg) {
		super(msg);
	}
}
