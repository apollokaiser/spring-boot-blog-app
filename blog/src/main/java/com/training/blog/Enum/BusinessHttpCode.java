package com.training.blog.Enum;

import lombok.Getter;

/**
 * @author ThinhNgoHoai
 * @param
 */


@Getter
public enum BusinessHttpCode {
    USER_NOT_FOUND(1001, "User do not exist"),
    USER_ALREADY_EXIST(1002, "User already exists"),
    USER_NOT_ACTIVATED(1003, "User not activated"),
    ACCOUNT_ASSOCIATES_FAILED(1004,"User has already been assigned to another account"),
    EMAIL_REGISTERED(1005,"Your email address is registered as an user"),
    WRONG_PASSWORD(1004, "Password is incorrect"),
    NO_CHANGE_APPLY(2000, "No data will be updated"),
    INVALID_TOKEN(3001, "Invalid token"),
    EXPIRED_TOKEN(3002, "Token is expired"),
    INVALID_REFRESH_TOKEN(3003, "Refresh Token is invalid"),
    EXPIRED_REFRESH_TOKEN(3004, "Refresh Token is expired"),
    ERROR_MAIL(5001, "Can't send email"),
    NULL_POINTER_REQUEST_DATA(5002, "Request data might have been be null"),
    NOT_ALLOW_DATA_SOURCE(5003, "Cannot allowed data that was requested by another source"),
    ;
    private final int status;
    private final String description;
    BusinessHttpCode(int status, String description) {
        this.status = status;
        this.description = description;
    }
}
