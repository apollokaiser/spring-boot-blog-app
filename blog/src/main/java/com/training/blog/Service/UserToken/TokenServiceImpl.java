package com.training.blog.Service.UserToken;

import com.training.blog.DAO.Token.TokenDao;
import com.training.blog.Entities.User_Token;
import com.training.blog.Exception.CustomException.NotFoundEntityException;
import com.training.blog.Exception.CustomException.TokenExpiredException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
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
    public void validateUserToken(String token) {
        User_Token user_token = tokenDao.findByToken(token).orElseThrow(()->
                new NotFoundEntityException("Token not found", User_Token.class));
        Date expiration = new Date(user_token.getExpiresAt());
        if(expiration.before(new Date())){
            throw new TokenExpiredException("Token has expired");
        }
       tokenDao.validatedToken(user_token);
    }
}
