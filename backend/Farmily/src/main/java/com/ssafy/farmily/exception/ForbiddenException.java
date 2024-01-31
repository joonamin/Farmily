package com.ssafy.farmily.exception;

public class ForbiddenException extends RuntimeException {
	public ForbiddenException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ForbiddenException(String msg) {
		this(msg, null);
	}

	public ForbiddenException(Throwable cause) {
		super("접근이 거부되었습니다.", cause);
	}

	public ForbiddenException() {
		this((Throwable) null);
	}
}
