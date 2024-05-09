package com.training.blog.Exception.CustomException;

import java.util.concurrent.TimeoutException;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}
