package com.training.blog.Service.InvalidToken;

import com.training.blog.Entities.InvalidToken;

public interface InvalidTokenService {
    void save(String token);
    void delete(String id);
    void findByTokenId(String id);
}
