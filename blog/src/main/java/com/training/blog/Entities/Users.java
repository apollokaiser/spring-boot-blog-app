package com.training.blog.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name="last_name", columnDefinition = "TEXT")
    private String firstName;
    @Column(name="last_name", columnDefinition = "VARCHAR(200)")
    private String lastName;
    @Column(name="email", columnDefinition = "VARCHAR(255)")
    private String email;
    private String password;
    @Column(name="avatar", columnDefinition = "VARCHAR(255)")
    private String avatar;
    private LocalDateTime lastLogin;
    @Column(name="intro", columnDefinition = "TEXT")
    private String intro;
    @Column(name="profile" ,columnDefinition = "TEXT")
    private String profile;
    @Transient
    private String name;
    public String getName(){
        return this.firstName + " " + this.lastName;
    }
}
