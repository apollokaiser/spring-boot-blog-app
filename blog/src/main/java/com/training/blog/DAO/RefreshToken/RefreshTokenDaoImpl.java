package com.training.blog.DAO.RefreshToken;

import com.training.blog.DAO.User.UserDao;
import com.training.blog.Entities.RefreshToken;
import com.training.blog.Entities.Users;
import com.training.blog.Exception.CustomException.NotFoundEntityException;
import com.training.blog.Repositories.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
@AllArgsConstructor
public class RefreshTokenDaoImpl implements RefreshTokenDao{

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDao userDao;
    @Override
    public RefreshToken save(RefreshToken entity) {
       Users user = userDao.findUsersByEmail(entity.getUser().getEmail())
                        .orElseThrow(()-> new NotFoundEntityException("User not found"));
       entity.setUser(user);
        return refreshTokenRepository.save(entity);
    }

    @Override
    public void delete(List<Long> ids) {

    }

    @Override
    public void update(RefreshToken entity) {

    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }
}
