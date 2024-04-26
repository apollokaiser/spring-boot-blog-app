package com.training.blog.Service.User;

import com.training.blog.DAO.User.UserDao;
import com.training.blog.Entities.User_Token;
import com.training.blog.Entities.Users;
import com.training.blog.Payload.RegistationRequest;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {
    private final RegisterService registerService;
    private final UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new MyUserDetail(userDao.findUsersByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found")));
    }


    public void register(RegistationRequest registationRequest) {
        registerService.register(Users.builder()
                .email(registationRequest.getEmail())
                .password(registationRequest.getPassword())
                .build());
    }
}
