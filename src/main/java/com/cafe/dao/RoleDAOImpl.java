package com.cafe.dao;

import com.cafe.db.Database;
import com.cafe.model.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 18011129 Lorenz Soriano & 21143576 Phoebe Cruz
 */

public class RoleDAOImpl implements RoleDAO {

    public RoleDAOImpl() throws SQLException {
        // No longer need to store connection as instance variable
        // Each method will get a fresh connection
    }

    @Override
    public void addRole(Role role) {
        String sql = "INSERT INTO role (name) VALUES (?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, role.getName());
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                role.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Role getRoleById(Long id) {
        String sql = "SELECT * FROM role WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Role role = new Role();
                role.setId(rs.getLong("id"));
                role.setName(rs.getString("name"));
                return role;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Role getRoleByName(String name) {
        String sql = "SELECT * FROM role WHERE name = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Role role = new Role();
                role.setId(rs.getLong("id"));
                role.setName(rs.getString("name"));
                return role;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM role";

        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getLong("id"));
                role.setName(rs.getString("name"));
                roles.add(role);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

    @Override
    public void updateRole(Role role) {
        String sql = "UPDATE role SET name=? WHERE id=?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, role.getName());
            stmt.setLong(2, role.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRole(Long id) {
        String sql = "DELETE FROM role WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                System.out.println("Role with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No role found with ID " + id + ".");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting role: " + e.getMessage());
        }
    }
}