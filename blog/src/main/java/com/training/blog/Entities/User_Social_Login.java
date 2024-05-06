package com.training.blog.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_social_login")
@NoArgsConstructor
@AllArgsConstructor
public class User_Social_Login extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String provider;
    private String provider_id;
    private String access_token;
    private Long expires_in;
    @ManyToOne
    @JoinColumn(name = "user_id",
            referencedColumnName = "user_id")
    private Users user;
}
