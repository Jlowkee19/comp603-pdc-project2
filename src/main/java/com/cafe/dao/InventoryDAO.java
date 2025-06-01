/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cafe.dao;

import com.cafe.model.InventoryItem;
import java.util.List;

/**
 *
 * @author justlowkee
 */

public interface InventoryDAO {
    void addItem(InventoryItem item);
    InventoryItem getItem(int id);
    List<InventoryItem> getAllItems();
    void updateItem(InventoryItem item);
    void deleteItem(int id);
    public void printAllInventory();
    
}
