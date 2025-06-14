package com.cafe.dao;

import com.cafe.model.Role;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 18011129 Lorenz Soriano & 21143576 Phoebe Cruz
 */

public interface RoleDAO {
    void addRole(Role role);
    Role getRoleById(Long id);
    Role getRoleByName(String name);
    List<Role> getAllRoles() throws SQLException;
    void updateRole(Role role);
    void deleteRole(Long id);
}