package com.slf.main.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception e, WebRequest webRequest) {
		StackTraceElement[] stackTraceElements = e.getStackTrace();
		ExceptionResponse errorDetails = null;
		for (StackTraceElement stackTraceElement : stackTraceElements) {
			String className = stackTraceElement.getClassName();
			if (className.startsWith("com.groupbima")) {
				errorDetails = new ExceptionResponse(new Date(), e.toString(), stackTraceElement.toString(), webRequest.getDescription(false));
				break;
			}
		}
		if (errorDetails == null)
			errorDetails = new ExceptionResponse(new Date(), e.toString(), stackTraceElements[0].toString(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotNullException.class)
	public ResponseEntity<ExceptionResponse> handleNotNullException(NotNullException e, WebRequest webRequest) {
		ExceptionResponse errorDetails = new ExceptionResponse(new Date(), e.toString(), e.getStackTrace()[0].toString(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(JWTMissingException.class)
	public ResponseEntity<ExceptionResponse> handleJWTMissingException(JWTMissingException e, WebRequest webRequest) {
		ExceptionResponse errorDetails = new ExceptionResponse(new Date(), e.toString(), e.getStackTrace()[0].toString(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(MalformedJwtException.class)
	public ResponseEntity<ExceptionResponse> handleMalformedJWTException(MalformedJwtException e, WebRequest webRequest) {
		ExceptionResponse errorDetails = new ExceptionResponse(new Date(), e.toString(), e.getStackTrace()[0].toString(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ExceptionResponse> handleExpiredJWTException(ExpiredJwtException e, WebRequest webRequest) {
		ExceptionResponse errorDetails = new ExceptionResponse(new Date(), e.toString(), e.getStackTrace()[0].toString(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<ExceptionResponse> handleJWTSignatureException(SignatureException e, WebRequest webRequest) {
		ExceptionResponse errorDetails = new ExceptionResponse(new Date(), e.toString(), e.getStackTrace()[0].toString(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(UnsupportedJwtException.class)
	public ResponseEntity<ExceptionResponse> handleUnsupportedJwtException(UnsupportedJwtException e, WebRequest webRequest) {
		ExceptionResponse errorDetails = new ExceptionResponse(new Date(), e.toString(), e.getStackTrace()[0].toString(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e, WebRequest webRequest) {
		ExceptionResponse errorDetails = new ExceptionResponse(new Date(), e.toString(), e.getStackTrace()[0].toString(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest webRequest) {
		String errorMessage = e.getMessage();
		int index = errorMessage.lastIndexOf("default message");
		errorMessage = errorMessage.substring(index + 17, errorMessage.length() - 3);
		ExceptionResponse errorDetails = new ExceptionResponse(new Date(), errorMessage, e.getStackTrace()[0].toString(), webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

}
