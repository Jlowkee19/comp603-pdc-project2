package com.cafe.dao;


import com.cafe.db.Database;
import com.cafe.model.InventoryItem;
import com.cafe.model.InventoryItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAOImpl implements InventoryDAO {

    @Override
    public void addItem(InventoryItem item) {
        
        if (item == null || item.getName() == null || item.getName().trim().isEmpty()) {
        throw new IllegalArgumentException("Item name cannot be null or empty");
        }
        if (item.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (item.getUnitPrice() <= 0) {
            throw new IllegalArgumentException("Unit price must be positive");
        }
        
        String sql = "INSERT INTO inventory (name, category, quantity, unit_price) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getCategory());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getUnitPrice());
            stmt.executeUpdate();
            System.out.println("Item added successfully");

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // Unique constraint violation
                System.out.println("Item '" + item.getName() + "' (" + item.getCategory() + ") already exists");
            } else {
                throw new RuntimeException("Error adding item", e);
            }
        }
    }

    @Override
    public InventoryItem getItem(int id) {
        String sql = "SELECT * FROM inventory WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                InventoryItem item = new InventoryItem();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCategory(rs.getString("category"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("unit_price"));
                return item;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting item", e);
        }
    }

    @Override
    public List<InventoryItem> getAllItems() {
        List<InventoryItem> items = new ArrayList<>();
        String sql = "SELECT * FROM inventory";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                InventoryItem item = new InventoryItem();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCategory(rs.getString("category"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("unit_price"));
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all items", e);
        }
    }

    @Override
    public void updateItem(InventoryItem item) {
        String sql = "UPDATE inventory SET name=?, category=?, quantity=?, unit_price=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getCategory());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getUnitPrice());
            stmt.setInt(5, item.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating item", e);
        }
    }

    @Override
    public void deleteItem(int id) {
        String sql = "DELETE FROM inventory WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting item", e);
        }
    }
    
    public void printAllInventory() {
    String sql = "SELECT * FROM inventory";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nCurrent Inventory:");
            System.out.println("ID\tName\t\tCategory\tQuantity\tPrice");
            System.out.println("------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%d\t%-15s\t%-10s\t%d\t\t$%.2f%n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("quantity"),
                    rs.getDouble("unit_price"));
            }
        } catch (SQLException e) {
            System.err.println("Error printing inventory: " + e.getMessage());
        }
    }
    
}