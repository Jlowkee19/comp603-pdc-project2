package com.cafe.db;


import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class Database {
    
    private static Connection conn = null;
    private static boolean isInitialised = false;
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
    
    public static void shutdownDatabase(){
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                }
            } catch (SQLException e){
                System.err.println("Error closing connection: " + e.getMessage());
            } finally { 
                conn = null;
        }
        // Only shutdown Derby if we're actually shutting down the application
        // Don't shutdown during clean operations
    }
    
    public static void shutdownDerby(){
        try { 
            // Shutdown Derby engine 
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            // Expected exception during shutdown
            if (e.getSQLState() != null && e.getSQLState().equals("XJ015")){
                System.out.println("Derby shutdown normally");
            } else {
                System.err.println("Unexpected error during Derby shutdown: " + e.getMessage());
            }   
        } finally {
           conn = null;
           isInitialised = false;
        }
    }
    
    private static void initializeDatabase() {
        try (InputStream is = Database.class.getResourceAsStream("/init_db.sql")) {
            if (is == null) throw new FileNotFoundException("init_db.sql not found");

            String sql = new String(is.readAllBytes());
            String[] statements = sql.split(";\\s*");

            try (Statement stmt = conn.createStatement()) {
                for (String statement : statements) {
                    if (!statement.trim().isEmpty()) {
                        try {
                            stmt.executeUpdate(statement);
                        } catch (SQLException e) {
                            // Ignore "already exists" errors and duplicate key errors
                            if (!e.getSQLState().equals("X0Y32") && !e.getSQLState().equals("23505")) {
                                throw e;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("DATABASE INIT FAILED: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public static void cleanDatabase() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DROP TABLE IF EXISTS orders");
                stmt.executeUpdate("DROP TABLE IF EXISTS inventory");
            }
            conn.close();
            conn = null;
        }
    }
    
}