package com.cafe.db;


import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class Database {
    private static Connection conn = null;
    
    private Database() {} // Prevent instantiation
    
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            // Load DB properties (embedded mode)
            String url = "jdbc:derby:cafeDB;create=true";
            conn = DriverManager.getConnection(url);
            initializeDatabase(); // Create tables if missing
        }
        return conn;
    }
    
    private static void initializeDatabase() {
        try (InputStream is = Database.class.getResourceAsStream("/init_db.sql");
             Statement stmt = conn.createStatement()) {
            // Read SQL script
            String sql = new String(is.readAllBytes());
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }
}