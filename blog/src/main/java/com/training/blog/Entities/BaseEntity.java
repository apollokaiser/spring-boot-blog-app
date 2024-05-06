package com.training.blog.Entities;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    private Long createdAt;
    @LastModifiedDate
    private Long modifiedAt;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private Users createBy;
    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "modified_by")
    private Users modifiedBy;
}
