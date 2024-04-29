package com.training.blog.DAO.Token;

import com.training.blog.Entities.User_Token;
import com.training.blog.Entities.Users;
import com.training.blog.Repositories.TokenRepository;
import com.training.blog.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class TokenDaoImpl implements TokenDao{
    private final TokenRepository repository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public void save(User_Token entity) {
        Users user = userRepository.findByEmail(entity.getUser().getEmail())
                .orElseThrow( () -> new IllegalStateException("Could not find user"));
        entity.setUser(user);
        try {
        repository.save(entity);
        }catch (Exception e) {
            System.out.println("Saving Token failed: /n" + e.getMessage());
          return;
        }
    }

    @Override
    public void delete(List<Long> ids) {
        // not use this method (may be)
    }

    @Override
    public void update(User_Token entity) {
        // not use this method (may be)
    }
}
