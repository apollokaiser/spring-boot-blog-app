package com.training.blog.Repositories;

import com.training.blog.Entities.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {
}
