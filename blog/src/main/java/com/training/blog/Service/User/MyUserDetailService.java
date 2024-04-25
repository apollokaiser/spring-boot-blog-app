package com.training.blog.Service.User;

import com.training.blog.DAO.User.UserDao;
import com.training.blog.Entities.Users;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new MyUserDetail(userDao.findUsersByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found")));
    }
}
