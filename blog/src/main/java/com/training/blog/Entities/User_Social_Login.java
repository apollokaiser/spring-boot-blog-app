package com.training.blog.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_social_login")
@NoArgsConstructor
@AllArgsConstructor
public class User_Social_Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;
    private int user_id;
    private String provider;
    private String provider_id;
    private String access_token;
    private Long expires_in;
}
