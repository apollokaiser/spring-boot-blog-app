package com.training.blog.Config;

import com.training.blog.Entities.Roles;
import com.training.blog.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class ApplicationStarter {
    @Autowired
    private RoleRepository roleRepository;
    private Set<Roles> roles = new HashSet<>(){{
       add(Roles.builder().role("ROLE_ADMIN").build());
       add(Roles.builder().role("ROLE_USER").build());
    }};
    @Bean
    public ApplicationRunner appRunner(){
        return args -> {
            System.out.println("Application started ...");
            if(roleRepository.count() == 0) {
                roleRepository.saveAll(roles);
            }
        };
    }
}
