package com.reactivespring.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ErrorEnum error;

    public ApiException(ErrorEnum message) {
        super(message.getMessage());
        this.error = message;
    }
}
