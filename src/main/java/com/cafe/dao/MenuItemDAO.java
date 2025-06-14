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
 * @author renzzu19
 */

public interface MenuItemDAO {
    List<MenuItem> getAvailableMenuItems() throws SQLException;
    List<MenuItem> getMenuItemsByCategory(String category) throws SQLException;
}
