package com.cafe.gui;


import com.cafe.dao.UserAccountDAO;
import com.cafe.dao.UserAccountDAOImpl;
import com.cafe.model.Role;
import com.cafe.model.UserAccount;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class RegisterFrame extends JFrame {

    private JTextField firstnameField, surnameField, usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

    private final UserAccountDAO userDao;

    public RegisterFrame() throws SQLException {
        userDao = new UserAccountDAOImpl();
        initUI();
    }

    private void initUI() {
        setTitle("User Registration");
        setSize(300, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel firstnameLabel = new JLabel("First Name:");
        firstnameLabel.setBounds(20, 20, 80, 25);
        add(firstnameLabel);

        firstnameField = new JTextField();
        firstnameField.setBounds(100, 20, 160, 25);
        add(firstnameField);

        JLabel surnameLabel = new JLabel("Surname:");
        surnameLabel.setBounds(20, 50, 80, 25);
        add(surnameLabel);

        surnameField = new JTextField();
        surnameField.setBounds(100, 50, 160, 25);
        add(surnameField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 80, 80, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 80, 160, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 110, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 110, 160, 25);
        add(passwordField);

        registerButton = new JButton("Register");
        registerButton.setBounds(90, 160, 100, 30);
        registerButton.addActionListener(this::registerAction);
        add(registerButton);
    }

    private void registerAction(ActionEvent e) {
        UserAccount user = new UserAccount();
        user.setFirstname(firstnameField.getText());
        user.setSurname(surnameField.getText());
        user.setUsername(usernameField.getText());
        user.setPassword(String.valueOf(passwordField.getPassword()));
        user.setActive(true);
        user.setRole(new Role(null, "user")); // default role

        userDao.addUser(user);
        JOptionPane.showMessageDialog(this, "User registered successfully!");
        dispose();
    }
}