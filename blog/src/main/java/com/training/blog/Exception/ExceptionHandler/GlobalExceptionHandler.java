package com.training.blog.Exception.ExceptionHandler;

import com.training.blog.Entities.User_Token;
import com.training.blog.Entities.Users;
import com.training.blog.Exception.CustomException.NotFoundEntityException;
import com.training.blog.Exception.CustomException.PasswordErrorException;
import com.training.blog.Exception.CustomException.TokenExpiredException;
import com.training.blog.Payload.ResponseMessage;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.training.blog.Enum.BusinessErrorCode.*;
import static org.springframework.http.HttpStatus.OK;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(LockedException.class)
   public ResponseEntity<ResponseMessage> handleException(LockedException ex){
        ResponseMessage message =
                ResponseMessage.builder()
                        .status(USER_NOT_ACTIVATED.getStatus())
                        .description(USER_NOT_ACTIVATED.getDescription())
                        .message(ex.getMessage())
                        .build();
        return ResponseEntity
                .status(OK)
                .body(message);
    }
    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<ResponseMessage> handleException(NotFoundEntityException ex){
        ResponseMessage message = null;
        if(ex.getErrorEntity() == Users.class){
            message = ResponseMessage.builder()
                    .status(USER_NOT_FOUND.getStatus())
                    .description(USER_NOT_FOUND.getDescription())
                    .message(ex.getMessage())
                    .build();
        }
        if(ex.getErrorEntity() == User_Token.class){
            message = ResponseMessage.builder()
                    .status(INVALID_TOKEN.getStatus())
                    .description(INVALID_TOKEN.getDescription())
                    .message(ex.getMessage())
                    .build();
        }
        return ResponseEntity.status(OK).body(message);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleException(UsernameNotFoundException ex){
        ResponseMessage message = ResponseMessage.builder()
                .status(USER_NOT_FOUND.getStatus())
                .description(USER_NOT_FOUND.getDescription())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(OK).body(message);
    }
    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ResponseMessage> handleException(MessagingException ex){
        ResponseMessage message = ResponseMessage.builder()
                .status(ERROR_MAIL.getStatus())
                .description(ERROR_MAIL.getDescription())
                .message(ex.getMessage())
                .build();
        return ResponseEntity
                .status(OK)
                .body(message);
    }
    @ExceptionHandler(PasswordErrorException.class)
    public ResponseEntity<ResponseMessage> handleException(PasswordErrorException ex){
        ResponseMessage message = ResponseMessage.builder()
                .status(WRONG_PASSWORD.getStatus())
                .description(WRONG_PASSWORD.getDescription())
                .message(ex.getMessage())
                .build();
        return ResponseEntity
                .status(OK)
                .body(message);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseMessage> handleException(NullPointerException ex){
        ResponseMessage message =
                ResponseMessage.builder()
                        .status(NULL_DATA.getStatus())
                        .description(NULL_DATA.getDescription())
                        .message(ex.getMessage())
                        .build();
        return ResponseEntity
                .status(OK)
                .body(message);
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ResponseMessage> handleException(TokenExpiredException ex){
        ResponseMessage message = ResponseMessage.builder()
                .status(EXPIRED_TOKEN.getStatus())
                .description(EXPIRED_TOKEN.getDescription())
                .message(ex.getMessage())
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
                                .description(e.getClass().getSimpleName())
                                .build();
        return ResponseEntity.status(OK).body(message);
    }
}
