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

    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "user",
            fetch = FetchType.EAGER)

    private RefreshToken refreshToken;
    @Transient
    private String name;
    public String getFullName()
    {
        return this.firstName + " " + this.lastName;
    }
    @OneToMany(mappedBy="user")
    private Set<ExternalUserAccount> accounts;

    @OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST})
    private Set<User_Token> tokens;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName ="role_id"))
    private Set<Roles> roles;
}
