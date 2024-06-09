package com.training.blog.DAO.InvalidToken;

import com.training.blog.Entities.InvalidToken;
import com.training.blog.Exception.CustomException.NotFoundEntityException;
import com.training.blog.Repositories.InvalidTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;
@Lazy
@Repository
@AllArgsConstructor
public class InvalidTokenDaoImpl implements InvalidTokenDao{

    private final InvalidTokenRepository invalidTokenRepository;
    @Override
    @Transactional
    public InvalidToken save(InvalidToken entity) {
        return invalidTokenRepository.save(entity);
    }

    @Override
    public void delete(List<String> ids) {
        invalidTokenRepository.deleteAllById(ids);
    }

    @Override
    public void update(InvalidToken entity) {
        // do not use
    }
}
