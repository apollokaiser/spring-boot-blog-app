package com.training.blog.Service.ExternalUserAccount;

import com.training.blog.DAO.ExternalUserAccount.ExternalUserAccountDao;
import com.training.blog.Entities.ExternalUserAccount;
import com.training.blog.Payload.Response.ResponseMessage;
import com.training.blog.Payload.Response.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExternalUserAccountServiceImpl implements ExternalUserAccountService{

    private ExternalUserAccountDao externalUserAccountDao;

    @Override
    public Optional<ExternalUserAccount> findById(Long id) {
        return externalUserAccountDao.findById(id);
    }

    @Override
    public ExternalUserAccount save(ExternalUserAccount externalUserAccount) {
        return null;
    }

    @Override
    public Optional<ExternalUserAccount> findAccountByEmail(String email) {
        Optional<ExternalUserAccount> account = externalUserAccountDao.findAccountByEmail(email);
        if(account.isEmpty()){
            // not exist any account with that email
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public ResponseMessage proccess(UserInfo externalUserAccount) {
        return null;
    }
}
