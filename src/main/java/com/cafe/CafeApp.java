package com.cafe;

import com.cafe.dao.InventoryDAO;
import com.cafe.dao.InventoryDAOImpl;
import com.cafe.dao.UserAccountDAO;
import com.cafe.dao.UserAccountDAOImpl;
import com.cafe.db.Database;
import com.cafe.model.Role;
import com.cafe.model.UserAccount;
import com.formdev.flatlaf.FlatLightLaf;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author justlowkee
 */
public class CafeApp {    
    
    public static void main(String[] args) {
        FlatLightLaf.setup();
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down application...");
            Database.shutdownDerby();
        }));
        
        try {
            // Database setup and testing
            setupDatabase();
            
            // Start the GUI application
            startApplication();
            
        } catch (SQLException e) {
            System.err.println("FATAL DATABASE ERROR: " + e.getMessage());
            e.printStackTrace();
            Database.shutdownDerby();
            System.exit(1); // Exit if database setup fails
        } catch (Exception e) {
            System.err.println("FATAL APPLICATION ERROR: " + e.getMessage());
            e.printStackTrace();
            Database.shutdownDerby();
            System.exit(1);
        }
    }
    
    private static void setupDatabase() throws SQLException {
        System.out.println("=== DATABASE SETUP ===");
        
        // Clean up for development
        Database.cleanDatabase();
        System.out.println("Database cleaned.");
        
        System.out.println("Connecting to database...");
        Database.getConnection(); // Triggers initialization
        System.out.println("Database connection established!");
        
        // Test inventory operations
        System.out.println("\n=== INVENTORY TEST ===");
        InventoryDAO inventoryDao = new InventoryDAOImpl();
        
        // Uncomment to add test items
        // inventoryDao.addItem(new InventoryItem("Latte", "Coffee", 100, 5.50));
        // inventoryDao.addItem(new InventoryItem("Cappuccino", "Coffee", 80, 4.75));
        // inventoryDao.addItem(new InventoryItem("Croissant", "Pastry", 50, 3.25));
        
        inventoryDao.printAllInventory();
        
        // Test user account operations
        System.out.println("\n=== USER ACCOUNT TEST ===");
        UserAccountDAO userDao = new UserAccountDAOImpl();
       
        ((UserAccountDAOImpl) userDao).printAllUsers();
        
        // Uncomment to delete specific users
        // userDao.deleteUser(402);
       //userDao.deleteUser(1);
        
        System.out.println("Database setup complete!");
    }

    private static void startApplication() {
        System.out.println("\n=== STARTING APPLICATION ===");
        
        // Set up GUI on the Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                // Start the application controller which will show the login frame
                new CafeController();
                System.out.println("Application started successfully!");
            } catch (Exception e) {
                System.err.println("Error starting GUI application: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}