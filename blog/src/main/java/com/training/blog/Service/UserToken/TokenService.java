package com.training.blog.Service.UserToken;


import com.training.blog.Entities.User_Token;
import jakarta.mail.MessagingException;

public interface TokenService {
    User_Token findUserToken(String token);
    void saveUserToken(User_Token user_token);
    String validateUserToken(String token) throws MessagingException;
    void validateResetPasswordToken(String email, String token);
}
