package com.training.blog.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

// This entity is used to save the token that was invalid by logout
@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name="invalid_token", indexes = @Index(name = "index_token",columnList="id"))
public class InvalidToken extends BaseEntity {
    @Id
    @Column(name="id", columnDefinition = "VARCHAR(50) NOT NULL")
    private String id;
    @Column(name="expiration", columnDefinition = "TIMESTAMP")
   private Long expiration;

}
