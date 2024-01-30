package com.ssafy.farmily.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtClaimException extends AuthenticationException {
	public InvalidJwtClaimException(String invalidClaim) {
		super(invalidClaim + " is invalid jwt claim");
	}
}
