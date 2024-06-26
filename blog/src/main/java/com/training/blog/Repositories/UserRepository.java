package com.training.blog.Repositories;

import com.training.blog.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    @Query("SELECT u FROM Users u WHERE u.refreshToken.refreshToken =?1")
    Optional<Users> findUsersByRefreshToken(String token);
}
