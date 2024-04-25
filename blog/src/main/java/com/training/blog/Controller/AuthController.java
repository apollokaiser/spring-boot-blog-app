package com.training.blog.Controller;

import com.training.blog.DAO.User.UserDao;
import com.training.blog.Entities.Users;
import com.training.blog.Payload.RegistationRequest;
import com.training.blog.Service.User.MyUserDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistationRequest register
    ) {
        MyUserDetail userDetail = (MyUserDetail) userDetailsService.loadUserByUsername(register.getEmail());
        if(userDetail == null) {
            userDao.save(Users.builder()
                    .email(register.getEmail())
                    .password(passwordEncoder.encode(register.getPassword())
                    ).build());
           // to be continue ....
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

}
