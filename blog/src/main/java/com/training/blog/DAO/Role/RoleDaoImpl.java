package com.training.blog.DAO.Role;

import com.training.blog.Entities.Roles;
import com.training.blog.Repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoleDaoImpl implements RoleDao{

    private final RoleRepository repository;
    @Override
    public Roles findRolesByRole(String role) {
        return repository.findRolesByRole(role);
    }

    @Override
    public Roles save(Roles entity) {
        return null;
    }

    @Override
    public void delete(List<Integer> ids) {

    }

    @Override
    public void update(Roles entity) {

    }
}
