package com.cafe.db;

import com.cafe.dao.RoleDAOImpl;
import com.cafe.dao.UserAccountDAOImpl;
import com.cafe.model.Role;
import com.cafe.model.UserAccount;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 18011129 Lorenz Soriano & 21143576 Phoebe Cruz
 */

public class TestDatabase {
    
    public static void main(String[] args) {
        System.out.println("=== CAFE DATABASE TEST ===");
        
        try {
            Connection conn = Database.getConnection();
            System.out.println("✓ Database connection successful!");
            
            // Display all users
            displayUsers();
            
            // Display all roles
            displayRoles();
            
            Database.shutdownDatabase();
        
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n TEST COMPLETE !!!");
    }
    
    private static void displayUsers() throws SQLException {
        System.out.println("\n=== USER ACCOUNTS ===");
        UserAccountDAOImpl userDAO = new UserAccountDAOImpl();
        List<UserAccount> users = userDAO.getAllUsers();
        
        if (users.isEmpty()) {
            System.out.println("No users found in database.");
        } else {
            for (UserAccount user : users) {
                System.out.println(user);
            }
        }
    }
    
    private static void displayRoles() throws SQLException {
        System.out.println("\n=== ROLES ===");
        RoleDAOImpl roleDAO = new RoleDAOImpl();
        List<Role> roles = roleDAO.getAllRoles();
        
        if (roles.isEmpty()) {
            System.out.println("No roles found in database.");
        } else {
            for (Role role : roles) {
                System.out.println(role);
            }
        }
    }
    
    
    
}
