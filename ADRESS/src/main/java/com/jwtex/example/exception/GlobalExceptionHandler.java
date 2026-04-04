package com.jwtex.example.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException)
	{
		ErrorResponse response = new ErrorResponse(resourceNotFoundException.getMessage(),resourceNotFoundException.getStatus());
		
		return new ResponseEntity<>(response,resourceNotFoundException.getStatus());	
	}
	
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException badRequestException)
	{
		ErrorResponse response = new ErrorResponse(badRequestException.getMessage(),badRequestException.getStatus());
		
		return new ResponseEntity<>(response,badRequestException.getStatus());	
	}
	
	@ExceptionHandler(MissingParameterException.class)
	public ResponseEntity<ErrorResponse> missingParameterException(MissingParameterException missingParameterException)
	{
		ErrorResponse response = new ErrorResponse(missingParameterException.getMessage(),missingParameterException.getStatus());
		
		return new ResponseEntity<>(response,missingParameterException.getStatus());	
	}
}
