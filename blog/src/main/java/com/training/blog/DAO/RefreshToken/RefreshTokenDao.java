package com.training.blog.DAO.RefreshToken;

import com.training.blog.DAO.GenericDao;
import com.training.blog.Entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenDao extends GenericDao<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}
