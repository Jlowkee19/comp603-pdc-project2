package com.cafe.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Test class for verifying Derby database connection
 * @author Enzo
 */
public class TestDatabase {
    
    public static void main(String[] args) {
        System.out.println("TESTING DERBY DATABASE CONNECTION");
        System.out.println("_________________________________");
        
        try {
            Connection conn = Database.getConnection();
            System.out.println("Database connection successful!");
            
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connection is active");
            }
            
            Connection conn2 = Database.getConnection();
            System.out.println("Second connection call successful!");
            
            Database.shutdownDatabase();
        
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("_________________________________");
        System.out.println("TEST COMPLETE");
    }
}
