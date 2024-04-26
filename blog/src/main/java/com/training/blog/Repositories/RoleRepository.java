package com.training.blog.Repositories;

import com.training.blog.Entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Roles, Integer> {
    @Query("SELECT r FROM Roles r WHERE r.role = ?1")
    Roles findRolesByRole(String role);
}
