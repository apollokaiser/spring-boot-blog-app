package com.training.blog.DAO.Token;

import com.training.blog.DAO.User.UserDao;
import com.training.blog.Entities.User_Token;
import com.training.blog.Entities.Users;
import com.training.blog.Exception.CustomException.DataNotMatchException;
import com.training.blog.Exception.CustomException.NotFoundEntityException;
import com.training.blog.Exception.CustomException.TokenExpiredException;
import com.training.blog.Repositories.TokenRepository;
import com.training.blog.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TokenDaoImpl implements TokenDao{

    private final TokenRepository repository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User_Token save(User_Token entity) {
        Users user = userRepository.findByEmail(entity.getUser().getEmail())
                .orElseThrow( () ->
                        new NotFoundEntityException("Could not find user", Users.class));
        entity.setUser(user);
       return repository.save(entity);
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
    public String validatedToken(String token) {
       User_Token user_token = repository.findByToken(token).orElseThrow(()->
               new NotFoundEntityException("Token not found", User_Token.class));
        Date expiration = new Date(user_token.getExpiresAt());
        if(expiration.before(new Date())){
            throw new TokenExpiredException("Token has expired");
        }
       user_token.setValidatesAt(System.currentTimeMillis());
       repository.save(user_token);
       return user_token.getUser().getEmail();
    }

    @Override
    @Transactional
    public void validateResetPasswordToken(String email, String token) {
        User_Token user_token = repository.findByToken(token).orElseThrow(()->
                new NotFoundEntityException("Token not found", User_Token.class));
        // check email and token
        if(!email.equals(user_token.getUser().getEmail()))
            throw new DataNotMatchException("Email and token is invalid");
       validatedToken(token);
    }

}
