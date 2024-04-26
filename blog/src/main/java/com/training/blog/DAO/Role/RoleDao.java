package com.training.blog.DAO.Role;

import com.training.blog.DAO.GenericDao;
import com.training.blog.Entities.Roles;

public interface RoleDao extends GenericDao<Roles, Integer> {
    Roles findRolesByRole(String role);

}
