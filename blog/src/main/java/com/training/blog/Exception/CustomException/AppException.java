package com.training.blog.Exception.CustomException;


import com.training.blog.Enum.BusinessHttpCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppException extends RuntimeException{
    BusinessHttpCode errorCode;
    Class<?> errorClass;
    public AppException(BusinessHttpCode errorCode){
        super(errorCode.getDescription());
        this.errorClass = null;
        this.errorCode = errorCode;
    }
    public AppException(BusinessHttpCode errorCode, Class<?> errorClass ){
        super(errorCode.getDescription());
        this.errorClass = errorClass;
        this.errorCode = errorCode;
    }
    public AppException(BusinessHttpCode errorCode, String message){
        super(message);
        this.errorClass = null;
        this.errorCode = errorCode;
    }
}
