package com.blog.api.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDetails> NotFoundExceptionHandler(NotFoundException ex, WebRequest web) {
		ErrorDetails ed = new ErrorDetails(LocalDate.now(), ex.getMessage(), web.getDescription(false));
		return new ResponseEntity<ErrorDetails>(ed, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorDetails> BadRequestExceptionHandler(BadRequestException ex, WebRequest web) {
		ErrorDetails ed = new ErrorDetails(LocalDate.now(), ex.getMessage(), web.getDescription(false));
		return new ResponseEntity<ErrorDetails>(ed, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDetails> GlobalExceptionHandler(RuntimeException ex, WebRequest web) {
		ErrorDetails ed = new ErrorDetails(LocalDate.now(), ex.getMessage(), web.getDescription(false));
		return new ResponseEntity<ErrorDetails>(ed, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
