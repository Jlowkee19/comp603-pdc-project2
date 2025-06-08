package com.cafe.dao;


import com.cafe.db.Database;
import com.cafe.model.UserAccount;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserAccountDAOImpl implements UserAccountDAO {

    private final Connection conn;

    public UserAccountDAOImpl() throws SQLException {
        this.conn = Database.getConnection();
    }

    @Override
    public void addUser(UserAccount user) {
        String sql = "INSERT INTO user_account (firstname, surname, username, password, active, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstname());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, user.getPassword());
            stmt.setBoolean(5, user.getActive() != null && user.getActive());
            stmt.setString(6, user.getRole() != null ? user.getRole().getName() : null);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with proper logging
        }
    }

    @Override
    public UserAccount getUserByUsername(String username) {
        String sql = "SELECT * FROM user_account WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UserAccount user = new UserAccount();
                user.setId(rs.getLong("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setSurname(rs.getString("surname"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setActive(rs.getBoolean("active"));

                // assuming Role is a simple object
                user.setRole(new com.cafe.model.Role(null, rs.getString("role")));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserAccount> getAllUsers() {
        List<UserAccount> users = new ArrayList<>();
        String sql = "SELECT * FROM user_account";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UserAccount user = new UserAccount();
                user.setId(rs.getLong("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setSurname(rs.getString("surname"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setActive(rs.getBoolean("active"));
                user.setRole(new com.cafe.model.Role(null, rs.getString("role")));

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void updateUser(UserAccount user) {
        String sql = "UPDATE user_account SET firstname=?, surname=?, password=?, active=?, role=? WHERE username=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstname());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getPassword());
            stmt.setBoolean(4, user.getActive() != null && user.getActive());
            stmt.setString(5, user.getRole() != null ? user.getRole().getName() : null);
            stmt.setString(6, user.getUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM user_account WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                System.out.println("User with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No user found with ID " + id + ".");
            }

        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
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
        List<UserAccount> users = getAllUsers();
        System.out.println("=== User Accounts ===");
        for (UserAccount user : users) {
            System.out.println(user);
        }
    }
   
   
   
}