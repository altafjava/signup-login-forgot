package com.slf.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NotNullException extends RuntimeException {

	private static final long serialVersionUID = 4041486312794102723L;

	public NotNullException() {
		super();
	}

	public NotNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotNullException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotNullException(String message) {
		super(message);
	}

	public NotNullException(Throwable cause) {
		super(cause);
	}

}
