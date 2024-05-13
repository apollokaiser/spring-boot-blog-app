package com.training.blog.Service.UserToken;

import com.training.blog.DAO.Token.TokenDao;
import com.training.blog.DAO.User.UserDao;
import com.training.blog.Entities.User_Token;
import com.training.blog.Exception.CustomException.NotFoundEntityException;
import com.training.blog.Exception.CustomException.TokenExpiredException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Lazy
@AllArgsConstructor
public class TokenServiceImpl implements TokenService{
    private final TokenDao tokenDao;
    @Override
    public User_Token findUserToken(String token) {
        return null;
    }

    @Override
    public void saveUserToken(User_Token user_token) {
        tokenDao.save(user_token);
    }

    @Override
    @Transactional
    public String validateUserToken(String token) {
        return tokenDao.validatedToken(token);
    }

    @Override
    public void validateResetPasswordToken(String email, String token) {
       tokenDao.validateResetPasswordToken(email, token);
    }
}
