package com.ssafy.farmily.exception;

public class NoSuchContentException extends RuntimeException {
	public NoSuchContentException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoSuchContentException(String msg) {
		this(msg, null);
	}

	public NoSuchContentException(Throwable cause) {
		super("해당되는 내용이 없습니다.", cause);
	}

	public NoSuchContentException() {
		this((Throwable) null);
	}
}
