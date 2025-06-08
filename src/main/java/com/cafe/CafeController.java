package com.cafe;


import com.cafe.dao.UserAccountDAOImpl;
import com.cafe.gui.CafeMain;
import com.cafe.gui.LoginFrame;
import com.cafe.model.UserAccount;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CafeController {

    private LoginFrame loginFrame = new LoginFrame();
    private CafeMain cafeMain = new CafeMain();

    public CafeController() {
        loginFrame.setVisible(true);

        loginFrame.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
         String username = loginFrame.getUsername();
         String password = loginFrame.getPassword();

         // Input validation
         if (username.isEmpty() || password.isEmpty()) {
             loginFrame.showError("Please enter both username and password");
             return;
         }

         UserAccountDAOImpl dao;
         boolean isValid = false; // Initialize the variable

         try {
             dao = new UserAccountDAOImpl();
             isValid = dao.validateLogin(username, password);
         } catch (SQLException e) {
             e.printStackTrace();
             loginFrame.showError("Database connection error. Please try again.");
             return; // Exit the method if there's a database error
         }

         // Now check the validation result
         if (isValid) {
             loginFrame.dispose();
             cafeMain.setVisible(true);
         } else {
             loginFrame.showError("Invalid username or password");
         }
     }
}
