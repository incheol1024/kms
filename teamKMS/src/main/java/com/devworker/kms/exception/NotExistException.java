package com.devworker.kms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotExistException extends RuntimeException{
	public NotExistException(String msg) {
		super(msg);
	}
	private static final long serialVersionUID = -5945499685182927203L;

}
