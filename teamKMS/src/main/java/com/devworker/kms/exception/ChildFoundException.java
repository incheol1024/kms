package com.devworker.kms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class ChildFoundException extends RuntimeException{
    public ChildFoundException(String msg) {
        super(msg);
    }

}
