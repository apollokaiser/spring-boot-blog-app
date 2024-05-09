package com.training.blog.Exception.CustomException;

public class PasswordErrorException extends DataNotMatchException{

    public PasswordErrorException(String message) {
        super(message);
    }
}
