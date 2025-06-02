package com.cafe.gui;

import com.cafe.dao.UserAccountDAO;
import com.cafe.dao.UserAccountDAOImpl;
import com.cafe.model.UserAccount;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserLoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    private final UserAccountDAO userDao;

    public UserLoginFrame() throws SQLException {
        userDao = new UserAccountDAOImpl();
        initUI();
    }

    private void initUI() {
        setTitle("User Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 20, 80, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 160, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 60, 80, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 60, 160, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(30, 100, 100, 25);
        loginButton.addActionListener(this::loginAction);
        add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(140, 100, 100, 25);
        registerButton.addActionListener(this::registerAction);
        add(registerButton);
    }

    private void loginAction(ActionEvent e) {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        UserAccount user = userDao.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(this, "Login successful! Welcome " + user.getFirstname());
            // Proceed to main app window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerAction(ActionEvent e) {
        SwingUtilities.invokeLater(() -> {
            try {
                new RegisterFrame().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(UserLoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new UserLoginFrame().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(UserLoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}