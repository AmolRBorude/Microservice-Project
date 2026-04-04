package com.jwtex.example.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException{

    private String message;
	
	private HttpStatus status;
	

	public BadRequestException(String message, HttpStatus status) {
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
