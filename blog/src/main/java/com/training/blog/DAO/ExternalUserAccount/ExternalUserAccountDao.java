package com.training.blog.DAO.ExternalUserAccount;

import com.training.blog.DAO.GenericDao;
import com.training.blog.Entities.ExternalUserAccount;

import java.util.Optional;

public interface ExternalUserAccountDao extends GenericDao<ExternalUserAccount, Long> {
    Optional<ExternalUserAccount> findById(Long id);
    Optional<ExternalUserAccount> findAccountByEmail(String email);
}
