package com.slf.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class JWTMissingException extends RuntimeException {

	private static final long serialVersionUID = 2466295155302006624L;

	public JWTMissingException() {
		super();
	}

	public JWTMissingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JWTMissingException(String message, Throwable cause) {
		super(message, cause);
	}

	public JWTMissingException(String message) {
		super(message);
	}

	public JWTMissingException(Throwable cause) {
		super(cause);
	}

}
