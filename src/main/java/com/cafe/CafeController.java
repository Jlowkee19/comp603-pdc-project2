package com.cafe;


import com.cafe.dao.UserAccountDAOImpl;
import com.cafe.gui.CafeMain;
import com.cafe.gui.LoginFrame;
import com.cafe.model.UserAccount;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


/**
 *
 * @author 18011129 Lorenz Soriano & 21143576 Phoebe Cruz
 */


public class CafeController {

    private LoginFrame loginFrame;
    private CafeMain cafeMain = new CafeMain();

    public CafeController() {
        setupLoginFrame();

        cafeMain.setLogoutCallback(() -> {
           setupLoginFrame();
            
        });
    }
    
    public void setupLoginFrame() {
        loginFrame = new LoginFrame();
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

         dao = new UserAccountDAOImpl();
         isValid = dao.validateLogin(username, password);

         // Now check the validation result
         if (isValid) {
             // Get the user and set it in the main window
             UserAccount user = dao.getUserByUsername(username);
             cafeMain.setCurrentUser(user);
             
             loginFrame.dispose();
             cafeMain.setVisible(true);

         } else {
             loginFrame.showError("Invalid username or password");
         }
     }
}
