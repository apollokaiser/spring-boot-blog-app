package com.training.blog.Service.RefreshToken;

import com.training.blog.Entities.RefreshToken;
import com.training.blog.Entities.Users;
import com.training.blog.Payload.Response.ResponseMessage;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Users user);
    RefreshToken handle(Users user,RefreshToken refreshToken);
    boolean checkRefreshToken(RefreshToken refreshToken);
    void save(RefreshToken entity);
    void delete(RefreshToken entity);
    Optional<RefreshToken> findByToken(String token);
    void update(RefreshToken entity);
}
