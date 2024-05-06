package com.training.blog.Enum;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessErrorCode {
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "User not found"),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "User already exists"),
    USER_NOT_ACTIVATED(HttpStatus.BAD_REQUEST, "User not activated"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "Password is incorrect"),
    NO_CODE(HttpStatus.NOT_FOUND, "No code "),
    ERROR_MAIL(HttpStatus.BAD_REQUEST, "Can't send email"),
    ;
    private final HttpStatus status;
     private final String description;;
    BusinessErrorCode(HttpStatus status, String description) {
        this.status = status;
        this.description = description;
    }
}
