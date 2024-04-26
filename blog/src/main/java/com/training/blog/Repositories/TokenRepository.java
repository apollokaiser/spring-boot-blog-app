package com.training.blog.Repositories;

import com.training.blog.Entities.User_Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<User_Token, Long> {
    @Query("SELECT t FROM User_Token t WHERE t.token = ?1")
    Optional<User_Token> findByToken(String token);
}
