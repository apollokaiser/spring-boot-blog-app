package com.training.blog.Entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name="refresh_token",
        indexes = @Index(name = "idx_refresh_token",
                        columnList = "refresh_token"))
public class RefreshToken extends BaseEntity{
    @Id
    private Long id;
    @Column(name = "refresh_token")
    private String refreshToken;
    @OneToOne
    @MapsId
    @PrimaryKeyJoinColumn(name = "user_id")
    private Users user;
}
