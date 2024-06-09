package com.training.blog.Exception.ExceptionHandler;

import com.training.blog.Enum.BusinessHttpCode;
import com.training.blog.Exception.CustomException.AppException;
import com.training.blog.Payload.Response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.training.blog.Enum.BusinessHttpCode.*;
import static org.springframework.http.HttpStatus.OK;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(LockedException.class)
   public ResponseEntity<ResponseMessage> handleException(LockedException ex){
        ResponseMessage message =
                ResponseMessage.errorBuilder()
                        .errorCode(USER_NOT_ACTIVATED)
                        .build();
        return ResponseEntity
                .status(OK)
                .body(message);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResponseMessage> handleException(AppException ex){
        ResponseMessage message = ResponseMessage.errorBuilder()
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .build();
        return ResponseEntity
               .status(OK)
               .body(message);
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ResponseMessage> handleException(MailException ex){
        ResponseMessage message = ResponseMessage.errorBuilder()
               .errorCode(ERROR_MAIL)
               .build();
        return ResponseEntity
               .status(OK)
               .body(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> handleUnwantedException(Exception e) {
       log.error(e.getMessage(),e.getClass().getSimpleName());
       ResponseMessage message = ResponseMessage.builder()
               .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .message(e.getMessage())
                                .build();
        return ResponseEntity.status(OK).body(message);
    }
}
