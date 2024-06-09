package com.training.blog.DAO.ExternalUserAccount;

import com.training.blog.Entities.ExternalUserAccount;
import com.training.blog.Enum.LoginType;
import com.training.blog.Repositories.ExternalUserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ExternalUserAccountDaoImpl implements ExternalUserAccountDao{


    private final ExternalUserAccountRepository externalUserAccountRepository;
    @Override
    public ExternalUserAccount save(ExternalUserAccount entity) {
        if(entity.getProvider() == null){
            entity.setProvider(LoginType.GOOGLE);
        }
        return externalUserAccountRepository.save(entity);
    }

    @Override
    public void delete(List<Long> ids) {

    }

    @Override
    public void update(ExternalUserAccount entity) {

    }

    @Override
    public Optional<ExternalUserAccount> findById(Long id) {
        return externalUserAccountRepository.findById(id);
    }

    @Override
    public Optional<ExternalUserAccount> findAccountByEmail(String email) {
       return externalUserAccountRepository.findAccountByEmail(email);
    }
}
