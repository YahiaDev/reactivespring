package com.reactivespring.exception;

import lombok.Getter;

@Getter
public class TeamException extends ApiException {

    public TeamException(ErrorEnum message) {
        super(message);
    }
}
