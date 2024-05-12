package com.training.blog.DAO.Token;

import com.training.blog.DAO.User.UserDao;
import com.training.blog.Entities.User_Token;
import com.training.blog.Entities.Users;
import com.training.blog.Exception.CustomException.NotFoundEntityException;
import com.training.blog.Repositories.TokenRepository;
import com.training.blog.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TokenDaoImpl implements TokenDao{

    private final TokenRepository repository;
    private final UserRepository userRepository;
    private final UserDao userDao;

    @Override
    @Transactional
    public void save(User_Token entity) {
        Users user = userRepository.findByEmail(entity.getUser().getEmail())
                .orElseThrow( () ->
                        new NotFoundEntityException("Could not find user", Users.class));
        entity.setUser(user);
        repository.save(entity);
    }

    @Override
    public void delete(List<Long> ids) {
        // not use this method (may be)
    }

    @Override
    public void update(User_Token entity) {
        // not use this method (may be)
    }

    @Override
    @Transactional
    public Optional<User_Token> findByToken(String token) {
        return repository.findByToken(token);
    }

    @Override
    @Transactional
    public void validatedToken(User_Token token) {
       User_Token user_token = repository.findByToken(token.getToken()).orElseThrow(()->
               new NotFoundEntityException("Token not found", User_Token.class));
       user_token.setValidatesAt(System.currentTimeMillis());
       repository.save(user_token);
       userDao.validatedUser(user_token.getUser().getEmail());
    }
}
