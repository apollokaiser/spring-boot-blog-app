package com.training.blog.DAO.Token;

import com.training.blog.DAO.GenericDao;
import com.training.blog.Entities.User_Token;

import java.util.Optional;

public interface TokenDao extends GenericDao<User_Token, Long> {
    Optional<User_Token> findByToken(String token);
    void validatedToken(User_Token token);
}
