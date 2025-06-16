/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cafe.dao;

import com.cafe.model.UserAccount;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 18011129 Lorenz Soriano & 21143576 Phoebe Cruz
 */

public interface UserAccountDAO {
    void addUser(UserAccount user) throws SQLException;
    UserAccount getUserByUsername(String username);
    List<UserAccount> getAllUsers() throws SQLException;
    void updateUser(UserAccount user) throws SQLException;
    void deleteUser(int id);
    public void printAllUsers();
    public void resetIdentitySequence();
}