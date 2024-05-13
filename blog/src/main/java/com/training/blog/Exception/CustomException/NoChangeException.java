package com.training.blog.Exception.CustomException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NoChangeException extends RuntimeException{
    Class<?> classException;
    public NoChangeException(String message) {
        super(message);
    }
    public NoChangeException(String message, Class<?> classException) {
        super(message);
        this.classException = classException;
    }
}
