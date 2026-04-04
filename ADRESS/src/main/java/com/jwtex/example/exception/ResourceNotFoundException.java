package com.jwtex.example.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException{
	
	private String message;
	
	private HttpStatus status;

	public ResourceNotFoundException(String message, HttpStatus status) {
		super();
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	

}
