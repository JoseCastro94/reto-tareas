package com.app.reto.exceptions;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorMessage {
	private Integer status;
    private String exception;
    private String message;
    private List<String> fieldErrors; 
    private String uri;  
    private LocalDateTime timestamp;
    
    public ErrorMessage() {}
    
	public ErrorMessage(Integer status, String exception, String message, 
			String uri, LocalDateTime timestamp) {
		super();
		this.status = status;
		this.exception = exception;
		this.message = message;
		this.uri = uri;
		this.timestamp = timestamp;
	}
	public ErrorMessage(Integer status, String exception, String message,List<String> fieldErrors, 
			String uri, LocalDateTime timestamp) {
		super();
		this.status = status;
		this.exception = exception;
		this.fieldErrors=fieldErrors;
		this.message = message;
		this.uri = uri;
		this.timestamp = timestamp;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public List<String> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<String> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
    
    
}
