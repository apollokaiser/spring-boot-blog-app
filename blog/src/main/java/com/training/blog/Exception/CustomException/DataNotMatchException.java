package com.training.blog.Exception.CustomException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class DataNotMatchException extends RuntimeException{
    private String message;
   public DataNotMatchException(String message){
        super(message);
        this.message = message;
    }
}
