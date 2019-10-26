package com.slf.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidJWTException extends RuntimeException {

	private static final long serialVersionUID = 2466295155302006624L;

	public InvalidJWTException() {
		super();
	}

	public InvalidJWTException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidJWTException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidJWTException(String message) {
		super(message);
	}

	public InvalidJWTException(Throwable cause) {
		super(cause);
	}

}
