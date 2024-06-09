package com.training.blog.Entities;

import com.training.blog.Enum.LoginType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "external_user_account")
@NoArgsConstructor
@AllArgsConstructor
public class ExternalUserAccount extends BaseEntity{
    @Id
    @Column(name="provider_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "provider", columnDefinition = "CHAR(10) NOT NULL")
    private LoginType provider;
    @ManyToOne
    @JoinColumn(name = "user_id",
            referencedColumnName = "user_id")
    private Users user;
}
