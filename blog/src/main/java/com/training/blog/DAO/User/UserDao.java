package com.training.blog.DAO.User;

import com.training.blog.DAO.GenericDao;
import com.training.blog.Entities.Users;
import com.training.blog.Exception.CustomException.NotFoundEntityException;

import java.util.Optional;

public interface UserDao extends GenericDao<Users, Long> {
    Optional<Users> findUsersByEmail(String email);
    Optional<Users> findUserById(Long id);
    void validatedUser(String email) throws NotFoundEntityException;
    void resetPassword(String email, String newPassword);
    void changePassword(String email, String newPassword);
    Optional<Users> getUsersByRefreshToken(String token);
    void saveRefreshToken( Users user, String refreshToken);
}
