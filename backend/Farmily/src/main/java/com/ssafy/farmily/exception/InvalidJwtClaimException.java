package com.ssafy.farmily.exception;

public class InvalidJwtClaimException extends RuntimeException {
	public InvalidJwtClaimException(String invalidClaim) {
		super(invalidClaim + " is invalid jwt claim");
	}
}
