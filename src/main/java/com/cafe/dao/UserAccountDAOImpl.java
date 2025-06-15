package com.cafe.dao;

import com.cafe.db.Database;
import com.cafe.model.UserAccount;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 18011129 Lorenz Soriano & 21143576 Phoebe Cruz
 */

public class UserAccountDAOImpl implements UserAccountDAO {

    public UserAccountDAOImpl() {
        // No longer need to store connection as instance variable
        // Each method will get a fresh connection
    }

    @Override
    public void addUser(UserAccount user) {
        if (getUserByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username '" + user.getUsername() + "' already exists!");
        }

        String sql = "INSERT INTO user_account (firstname, lastname, username, password, role, photo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstname());
            stmt.setString(2, user.getLastname());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole() != null ? user.getRole().getName() : null);
            stmt.setBytes(6, user.getPhoto());

            int result = stmt.executeUpdate();
            if (result == 0) {
                throw new RuntimeException("Failed to add user - no rows affected");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding user: " + e.getMessage(), e);
        } 
    }

    @Override
    public UserAccount getUserByUsername(String username) {
        String sql = "SELECT * FROM user_account WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UserAccount user = new UserAccount();
                user.setId(rs.getLong("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setPhoto(rs.getBytes("photo"));

                user.setRole(new com.cafe.model.Role(null, rs.getString("role")));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserAccount> getAllUsers() throws SQLException {
        List<UserAccount> users = new ArrayList<>();
        String sql = "SELECT * FROM user_account";

        try (Connection connection = Database.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UserAccount user = new UserAccount();
                user.setId(rs.getLong("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setPhoto(rs.getBytes("photo"));
                user.setRole(new com.cafe.model.Role(null, rs.getString("role")));

                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving users: " + e.getMessage(), e);
        }

        return users;
    }

    @Override
    public void updateUser(UserAccount user) throws SQLException {
        String sql = "UPDATE user_account SET firstname=?, lastname=?, password=?, role=?, photo=? WHERE id=?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstname());
            stmt.setString(2, user.getLastname());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole() != null ? user.getRole().getName() : null);
            stmt.setBytes(5, user.getPhoto());
            stmt.setLong(6, user.getId());

            int affected = stmt.executeUpdate();
            if (affected == 0) {
                throw new RuntimeException("User not found with ID: " + user.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM user_account WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                System.out.println("User with ID " + id + " deleted successfully.");
            } else {
                throw new RuntimeException("No user found with ID " + id + ".");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        }
    }
    
    public boolean validateLogin(String username, String password) {
        UserAccount user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
    
    

   public void printAllUsers() {
        try {
            List<UserAccount> users = getAllUsers();
            System.out.println("=== User Accounts ===");
            for (UserAccount user : users) {
                System.out.println(user);
            }
        } catch (SQLException e) {
            System.err.println("Error printing users: " + e.getMessage());
        }
    }
    
    
    public long getNextIdentityValue() {
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT MAX(id) + 1 AS next_id FROM user_account";
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("next_id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting next identity value: " + e.getMessage());
        }
        return 1; // Default if table is empty
    }

}