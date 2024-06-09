package com.training.blog.DAO.User;

import com.training.blog.DAO.Role.RoleDao;
import com.training.blog.Entities.Roles;
import com.training.blog.Entities.Users;
import com.training.blog.Enum.BusinessHttpCode;
import com.training.blog.Exception.CustomException.AppException;
import com.training.blog.Exception.CustomException.NoChangeException;
import com.training.blog.Repositories.UserRepository;
import com.training.blog.Exception.CustomException.NotFoundEntityException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.training.blog.Enum.BusinessHttpCode.USER_NOT_FOUND;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;
    private final RoleDao roleDao;

    @Override
    @Transactional
    public Users save(Users entity) {
            Roles role = roleDao.findRolesByRole("ROLE_USER");
        HashSet<Roles> roles = new HashSet<Roles>(){{ add(role); }};
        entity.setRoles(roles);
        return userRepository.save(entity);
    }

    @Override
    public void update(Users entity) {

    }

    @Override
    public Optional<Users> findUsersByEmail(String email) {
       return userRepository.findByEmail(email);
    }

    @Override
    public Optional<Users> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void validatedUser(String email) {
        Users user  = userRepository.findByEmail(email)
                .orElseThrow( () -> new AppException(USER_NOT_FOUND));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(()->
                        new AppException(USER_NOT_FOUND));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public void changePassword(String email, String newPassword) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(()->
                        new AppException(USER_NOT_FOUND));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public Optional<Users> getUsersByRefreshToken(String token) {
       return userRepository.findUsersByRefreshToken(token);
    }

    @Override
    @Transactional
    public void saveRefreshToken(Users user,String refreshToken) {

    }

    @Override
    public void delete(List<Long> ids) {
    }
}
