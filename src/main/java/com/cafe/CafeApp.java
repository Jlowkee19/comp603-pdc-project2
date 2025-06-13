package com.cafe;

import com.cafe.dao.RoleDAO;
import com.cafe.dao.RoleDAOImpl;
import com.cafe.dao.UserAccountDAO;
import com.cafe.dao.UserAccountDAOImpl;
import com.cafe.db.Database;
import com.formdev.flatlaf.FlatLightLaf;
import java.sql.SQLException;

/**
 * Main application class for the Cafe Management System
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
        
        // Database is ready
        System.out.println("Tables created and initialized successfully!");
        
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