package com.slf.main.exception;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = 2033832609328886554L;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss a", timezone = "IST")
	private Date timestamp;
	private String message;
	private String description;
	private String url;

	public ExceptionResponse() {
		super();
	}

	public ExceptionResponse(Date timestamp, String message, String description, String url) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
		this.url = url;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
