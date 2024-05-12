package com.training.blog.DAO.User;

import com.training.blog.DAO.Role.RoleDao;
import com.training.blog.Entities.Roles;
import com.training.blog.Entities.Users;
import com.training.blog.Repositories.UserRepository;
import com.training.blog.Exception.CustomException.NotFoundEntityException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;
    private final RoleDao roleDao;
    @Override
    @Transactional
    public void save(Users entity) {
            Roles role = roleDao.findRolesByRole("ROLE_USER");
           if (role == null) throw new NullPointerException("Role not found in role collection");
        if(entity == null)
            throw new NullPointerException("Saving user account have an issue that entity is null");
        HashSet<Roles> roles = new HashSet<Roles>();
        roles.add(role);
        entity.setRoles(roles);
        userRepository.save(entity);
    }

    @Override
    public void update(Users entity) {

    }

    @Override
    public Optional<Users> findUsersByEmail(String email) {
       return userRepository.findByEmail(email);
    }

    @Override
    public void validatedUser(String email) {
        Users user  = userRepository.findByEmail(email)
                .orElseThrow( () -> new NotFoundEntityException("User not found", Users.class));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        if(email.isEmpty()) throw new NullPointerException(("email is empty"));
        Users user = userRepository.findByEmail(email)
                .orElseThrow(()->
                        new NotFoundEntityException("User not found", Users.class));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public void delete(List<Long> ids) {

    }
}
