/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cafe.dao;

import com.cafe.model.UserAccount;
import java.util.List;

/**
 *
 * @author justlowkee
 */
public interface UserAccountDAO {
    void addUser(UserAccount user);
    UserAccount getUserByUsername(String username);
    List<UserAccount> getAllUsers();
    void updateUser(UserAccount user);
    void deleteUser(int id);
    public void printAllUsers();
}