package com.training.blog.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name="first_name", columnDefinition = "TEXT")
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
    @Column(name="enable" ,columnDefinition = "BIT")
    private boolean enabled;
    @Transient
    private String name;
    public String getFullName()
    {
        return this.firstName + " " + this.lastName;
    }
    @OneToMany(mappedBy="user")
    private Set<User_Social_Login> accounts;
    @OneToMany(mappedBy="user")
    private Set<User_Token> tokens;
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Roles> roles;
}
