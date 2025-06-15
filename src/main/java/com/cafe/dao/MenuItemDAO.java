/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cafe.dao;

import com.cafe.model.MenuItem;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 18011129 Lorenz Soriano & 21143576 Phoebe Cruz
 */

public interface MenuItemDAO {
    List<MenuItem> getAvailableMenuItems() throws SQLException;
    List<MenuItem> getMenuItemsByCategory(String category) throws SQLException;
}
