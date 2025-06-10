/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cafe.db;

import java.sql.Connection;
import java.sql.SQLException;

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
            
            if (conn != null && !conn.isClosed()) {
                System.out.println("Connection is active");
        }
            // Test second connetion call 
            Connection conn2 = Database.getConnection();
            System.out.println("Second connection call successful!");
            
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
}
