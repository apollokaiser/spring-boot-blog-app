package com.training.blog.Service.UserToken;

import com.training.blog.DAO.Token.TokenDao;
import com.training.blog.Entities.User_Token;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void validateUserToken(String token) {

    }
}
