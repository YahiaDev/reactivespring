package com.reactivespring.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorEnum {
    TEAM_EXISTS_ERROR("Team Already Exists", HttpStatus.BAD_REQUEST),
    TEAM_NOT_EXISTS_ERROR("Team does not exists", HttpStatus.BAD_REQUEST),
    GAME_EXISTS_ERROR("the Game is already exists", HttpStatus.BAD_REQUEST),
    GAME_NOT_EXISTS_ERROR("The Game does not exist", HttpStatus.BAD_REQUEST);

    private String message;
    private HttpStatus httpStatus;
}
