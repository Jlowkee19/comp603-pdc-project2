package com.cafe;

import com.cafe.dao.InventoryDAO;
import com.cafe.dao.InventoryDAOImpl;
import com.cafe.dao.UserAccountDAO;
import com.cafe.dao.UserAccountDAOImpl;
import com.cafe.db.Database;
import com.cafe.model.InventoryItem;
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
        try {
            // Clean up for development
            Database.cleanDatabase();

            System.out.println("Connecting to database...");
            Database.getConnection(); // Triggers initialization
            System.out.println("Database setup complete!");

            InventoryDAO dao = new InventoryDAOImpl();
            //dao.addItem(new InventoryItem("Latte", "Coffee", 100, 5.50));
            dao.printAllInventory();
            
            UserAccountDAO userDao = new UserAccountDAOImpl();
            
            
            ((UserAccountDAOImpl) userDao).printAllUsers();
            
            // Delete user with ID 1 (change to desired ID)
            //userDao.deleteUser(402);
            //userDao.deleteUser(202);

            // Print after deletion
            //userDao.printAllUsers();

        } catch (SQLException e) {
            System.err.println("FATAL: " + e.getMessage());
        }
    }
}
