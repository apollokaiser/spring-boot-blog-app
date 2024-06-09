package com.training.blog.Service.InvalidToken;

import com.training.blog.DAO.InvalidToken.InvalidTokenDao;
import com.training.blog.Entities.InvalidToken;
import com.training.blog.Enum.BusinessHttpCode;
import com.training.blog.Exception.CustomException.AppException;
import com.training.blog.Security.JWTAuth.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InvalidTokenServiceImpl implements InvalidTokenService{

    private final InvalidTokenDao invalidTokenDao;

    private JWTService JwtService;

    @Override
    public void save(String token) {
        //get Jwt id
        String id = JwtService.extractId(token);
        //check if token is valid and not expired
        if(!JwtService.isTokenExpired(token) && id !=null){
            InvalidToken invalidToken =InvalidToken.builder()
                    .id(id)
                    .expiration(JwtService.extractAllClaims(token)
                            .getExpiration().getTime())
                    .build();
            invalidTokenDao.save(invalidToken);
        }
        else {
            throw new AppException(BusinessHttpCode.INVALID_TOKEN);
        }
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void findByTokenId(String id) {

    }
}
