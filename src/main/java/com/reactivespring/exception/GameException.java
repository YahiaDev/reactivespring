package com.reactivespring.exception;

import lombok.Getter;

@Getter
public class GameException extends ApiException {

    public GameException(ErrorEnum message) {
        super(message);
    }
}
