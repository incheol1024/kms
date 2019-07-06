package com.devworker.kms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotStackException extends RuntimeException{
    public NotStackException(String msg){
        super(msg);
    }

    public NotStackException(){ super();}

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
