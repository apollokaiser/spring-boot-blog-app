package com.training.blog.Service.RefreshToken;

import com.training.blog.DAO.RefreshToken.RefreshTokenDao;
import com.training.blog.Entities.RefreshToken;
import com.training.blog.Entities.Users;
import com.training.blog.Security.JWTAuth.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService{

    private final JWTService jwtService;

    private RefreshTokenDao refreshTokenDao;
    @Override
    public RefreshToken createRefreshToken(Users user) {
        if(user == null) return null;
        String token = jwtService.generateRefreshToken(user);
        return RefreshToken.builder()
                .refreshToken(token)
                .user(user).build();
    }

    @Override
    public RefreshToken handle(Users user,RefreshToken refreshToken) {
        //the first time login
        if(refreshToken == null){
            RefreshToken token = createRefreshToken(user);
            save(token);
            return token;
        } else if(!jwtService.isTokenExpired(refreshToken.getRefreshToken())) {
            RefreshToken token = createRefreshToken(user);
            refreshToken.setRefreshToken(token.getRefreshToken());
            update(refreshToken);
        }
            return refreshToken;

    }

    @Override
    public boolean checkRefreshToken(RefreshToken refreshToken) {
        if(refreshToken == null) return false;
        return jwtService.isValidToken(refreshToken.getRefreshToken());
    }

    @Override
    public void save(RefreshToken entity) {
        refreshTokenDao.save(entity);
    }

    @Override
    public void delete(RefreshToken entity) {

    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenDao.findByToken(token);
    }

    @Override
    public void update(RefreshToken entity) {

    }
}
