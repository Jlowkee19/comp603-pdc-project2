/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cafe.db;

import com.cafe.dao.UserAccountDAOImpl;
import com.cafe.model.UserAccount;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Enzo
 */
public class TestDatabase {
    public static void main(String args[]){
        System.out.println("TESTING DERBY DATABASE CONNECTION");
        System.out.println("_________________________________");
        
        try {
           // Test connection 
           Connection conn = Database.getConnection();
           System.out.println("Database connection succestful!");
            
           // Display all users
           displayUsers();
            
           // Shutdown test
           Database.shutdownDatabase();
        
       } catch (SQLException e) {
           System.err.println("Database error: " + e.getMessage());
           System.err.println("SQL State: " + e.getMessage());
           e.printStackTrace();
       } catch (Exception e){
           System.err.println("Unexpected error " + e.getMessage());
           e.printStackTrace();
       }
        System.out.println("_________________________________");
        System.out.println("TEST COMPLETE");
    }
    
    private static void displayUsers() throws SQLException {
        System.out.println("\n === USER ACCOUNTS ===");
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
    
    
}
