package com.training.blog.Service.ExternalUserAccount;

import com.training.blog.Entities.ExternalUserAccount;
import com.training.blog.Payload.Response.ResponseMessage;
import com.training.blog.Payload.Response.UserInfo;

import java.util.Optional;

public interface ExternalUserAccountService {
    Optional<ExternalUserAccount> findById(Long id);
    ExternalUserAccount save(ExternalUserAccount externalUserAccount);
    Optional<ExternalUserAccount> findAccountByEmail(String email);
    ResponseMessage proccess(UserInfo externalUserAccount);
}
