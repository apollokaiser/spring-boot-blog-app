package com.training.blog.DAO.User;

import com.training.blog.Entities.Users;
import com.training.blog.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;
    @Override
    @Transactional
    public void save(Users entity) {
        try {
        if(entity == null)
            throw new NullPointerException("entity is null");
        userRepository.save(entity);
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void update(Users entity) {

    }

    @Override
    public Optional<Users> findUsersByEmail(String email) {
        try {
           return userRepository.findUsersByEmail(email);
        } catch (Exception e) {
        return Optional.empty();
        }
    }

    @Override
    public void validatedUser(String email) {

    }
    @Override
    public void delete(List<Long> ids) {
        // not use this method (may be)
    }
}
