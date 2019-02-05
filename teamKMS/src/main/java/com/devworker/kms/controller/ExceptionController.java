package com.devworker.kms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionController {
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String handleError(Exception e) {
		e.printStackTrace();
		return (e.getMessage() != null) ? e.getMessage() : "Server Null Pointer Exception";
	}
}
