package com.training.blog.Service.User;

import com.training.blog.DAO.User.UserDao;
import com.training.blog.Entities.Users;
import com.training.blog.Payload.UserCredentialsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {


    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new MyUserDetail(userDao.findUsersByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found")));
    }
    public void login(UserCredentialsRequest loginRequest){
       Users user = userDao.findUsersByEmail(loginRequest.getEmail())
               .orElseThrow(()->new UsernameNotFoundException("User not found"));


    }
}
