package com.training.blog.Service.UserToken;


import com.training.blog.Entities.User_Token;

public interface TokenService {
    User_Token findUserToken(String token);
    void saveUserToken(User_Token user_token);
    void validateUserToken(String token);
}