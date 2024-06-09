package com.training.blog.Repositories;

import com.training.blog.Entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Query("SELECT r FROM RefreshToken r WHERE r.refreshToken = ?1")
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
