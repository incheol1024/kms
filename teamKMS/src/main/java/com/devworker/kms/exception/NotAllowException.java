package com.devworker.kms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class NotAllowException extends RuntimeException{
    public NotAllowException(String msg) {
        super(msg);
    }


}
