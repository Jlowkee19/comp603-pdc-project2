/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cafe.dao;

import com.cafe.db.Database;
import com.cafe.model.MenuItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 18011129 Lorenz Soriano & 21143576 Phoebe Cruz
 */

public class MenuItemDAOImpl implements MenuItemDAO {

    @Override
    public List<MenuItem> getAvailableMenuItems() throws SQLException {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE available = true ORDER BY category, name";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                MenuItem item = new MenuItem();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCategory(rs.getString("category"));
                item.setPrice(rs.getDouble("price"));
                item.setDescription(rs.getString("description"));
                item.setAvailable(rs.getBoolean("available"));
                menuItems.add(item);
            }
        } catch (SQLException e) {
            // If table doesn't exist, return sample data
            return getSampleMenuItems();
        }
        
        return menuItems;
    }

    @Override
    public List<MenuItem> getMenuItemsByCategory(String category) throws SQLException {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE category = ? AND available = true ORDER BY name";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MenuItem item = new MenuItem();
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setCategory(rs.getString("category"));
                    item.setPrice(rs.getDouble("price"));
                    item.setDescription(rs.getString("description"));
                    item.setAvailable(rs.getBoolean("available"));
                    menuItems.add(item);
                }
            }
        } catch (SQLException e) {
            // If table doesn't exist, return sample data filtered by category
            return getSampleMenuItems().stream()
                    .filter(item -> item.getCategory().equals(category))
                    .toList();
        }
        
        return menuItems;
    }
    
    private List<MenuItem> getSampleMenuItems() {
        List<MenuItem> sampleItems = new ArrayList<>();
        
        // Coffee items
        sampleItems.add(new MenuItem("Espresso", "Coffee", 3.50));
        sampleItems.add(new MenuItem("Americano", "Coffee", 4.00));
        sampleItems.add(new MenuItem("Cappuccino", "Coffee", 4.50));
        sampleItems.add(new MenuItem("Latte", "Coffee", 5.00));
        
        // Tea items
        sampleItems.add(new MenuItem("Green Tea", "Tea", 3.00));
        sampleItems.add(new MenuItem("Earl Grey", "Tea", 3.00));
        sampleItems.add(new MenuItem("Chai Latte", "Tea", 4.50));
        
        // Food items
        sampleItems.add(new MenuItem("Croissant", "Pastries", 3.25));
        sampleItems.add(new MenuItem("Blueberry Muffin", "Pastries", 3.75));
        sampleItems.add(new MenuItem("Chocolate Chip Cookie", "Pastries", 2.50));
        sampleItems.add(new MenuItem("Turkey Sandwich", "Sandwiches", 8.50));
        sampleItems.add(new MenuItem("Grilled Cheese", "Sandwiches", 6.00));
        
        return sampleItems;
    }
}