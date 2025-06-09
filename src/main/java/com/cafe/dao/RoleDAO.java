package com.cafe.dao;

import com.cafe.model.Role;
import java.util.List;

public interface RoleDAO {
    void addRole(Role role);
    Role getRoleById(Long id);
    Role getRoleByName(String name);
    List<Role> getAllRoles();
    void updateRole(Role role);
    void deleteRole(Long id);
}