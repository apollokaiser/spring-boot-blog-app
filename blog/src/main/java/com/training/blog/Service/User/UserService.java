package com.training.blog.Service.User;

import com.training.blog.DAO.User.UserDao;
import com.training.blog.Entities.Users;
import com.training.blog.Exception.CustomException.NotFoundEntityException;
import com.training.blog.Exception.CustomException.PasswordErrorException;
import com.training.blog.Mapper.UserMapper;
import com.training.blog.Models.UserDto;
import com.training.blog.Payload.ResponseMessage;
import com.training.blog.Payload.UserCredentialsRequest;
import com.training.blog.Security.JWTAuth.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JWTService jwtService;

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public ResponseMessage login(UserCredentialsRequest login){
        Users user  = userDao.findUsersByEmail(login.getEmail())
                .orElseThrow(()-> new NotFoundEntityException("email is not exists"));
        if(passwordEncoder.matches(login.getPassword(),user.getPassword()))
            throw new PasswordErrorException("password is wrong");
        UserDto userDto = userMapper.toUser(user);
        String jwt_token = jwtService.generateToken(new MyUserDetail(user));
        Map<String,Object> data = new HashMap<String,Object>(){{
            put("user", userDto);
            put("token", jwt_token);
        }};

    return ResponseMessage.builder()
            .status(HttpStatus.OK)
            .message("Login successfully")
            .data(data)
            .build();
    }
}
