package com.training.blog.Exception.CustomException;

public class NotFoundEntityException extends RuntimeException {

    public NotFoundEntityException() {
        super();
    }
    public NotFoundEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotFoundEntityException(String message) {
        super(message);
    }
}
