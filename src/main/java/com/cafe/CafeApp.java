package com.cafe;

import com.cafe.db.Database;
import java.sql.SQLException;

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
        try {
            System.out.println("Connecting to database...");
            Database.getConnection(); // Triggers initialization
            System.out.println("Database setup complete!");
        } catch (SQLException e) {
            System.err.println("FATAL: " + e.getMessage());
        }
    }
}
