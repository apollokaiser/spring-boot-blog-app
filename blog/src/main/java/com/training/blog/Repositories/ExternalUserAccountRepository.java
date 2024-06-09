package com.training.blog.Repositories;

import com.training.blog.Entities.ExternalUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExternalUserAccountRepository extends JpaRepository<ExternalUserAccount, Long> {
    @Query("SELECT e FROM ExternalUserAccount e where e.user.email=?1")
    Optional<ExternalUserAccount> findAccountByEmail(String email);
}
