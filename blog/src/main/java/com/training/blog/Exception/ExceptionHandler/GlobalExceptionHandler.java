package com.training.blog.Exception.ExceptionHandler;

import com.training.blog.Exception.CustomException.NotFoundEntityException;
import com.training.blog.Payload.ResponseMessage;
import com.training.blog.Enum.BusinessErrorCode;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LockedException.class)
   public ResponseEntity<ResponseMessage> handleException(LockedException ex){
        ResponseMessage message =
                ResponseMessage.builder()
                        .status(BusinessErrorCode.USER_NOT_ACTIVATED.getStatus())
                        .description(BusinessErrorCode.USER_NOT_ACTIVATED.getDescription())
                        .message(ex.getMessage())
                        .build();
        return ResponseEntity
                .status(401)
                .body(message);
    }
    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<ResponseMessage> handleException(NotFoundEntityException ex){
        ResponseMessage message =
                ResponseMessage.builder()
                        .status(BusinessErrorCode.USER_NOT_FOUND.getStatus())
                        .description(BusinessErrorCode.USER_NOT_FOUND.getDescription())
                        .message(ex.getMessage())
                        .build();
        return ResponseEntity
                .status(401)
                .body(message);
    }
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ResponseMessage> handleException(MessagingException ex){
        ResponseMessage message =
                ResponseMessage.builder()
                        .status(BusinessErrorCode.ERROR_MAIL.getStatus())
                        .description(BusinessErrorCode. ERROR_MAIL.getDescription())
                        .message(ex.getMessage())
                        .build();
        return ResponseEntity
                .status(401)
                .body(message);
    }
}
