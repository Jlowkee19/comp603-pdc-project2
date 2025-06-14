/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.cafe.gui.dialogs;

import com.cafe.dao.UserAccountDAO;
import com.cafe.dao.UserAccountDAOImpl;
import com.cafe.dao.RoleDAO;
import com.cafe.dao.RoleDAOImpl;
import com.cafe.model.UserAccount;
import com.cafe.model.Role;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author 18011129 Lorenz Soriano & 21143576 Phoebe Cruz
 */

public class UserAccDialog_2 extends javax.swing.JFrame {
    
    private UserAccountDAO userAccountDAO;
    private RoleDAO roleDAO;
    private UserAccount currentUser;
    private String selectedPhotoPath;
    private boolean isEditMode = false;
    private Runnable refreshCallback;

    /**
     * Creates new form UserAccDialog_2
     */
    public UserAccDialog_2() {
        super();
        initComponents();
        initializeDialog();
        setupPhotoClickListener();
        setupPlaceholders();
        loadRoles();
    }
    
    private void initializeDialog() {
        userAccountDAO = new UserAccountDAOImpl();
        try {
            roleDAO = new RoleDAOImpl();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error initializing role DAO: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        setLocationRelativeTo(null);
        setTitle("User Account Form");
        
        // Ensure images directory exists
        createImagesDirectory();
    }
    
    private void createImagesDirectory() {
        try {
            Path imagesPath = Paths.get("src/main/resources/images");
            if (!Files.exists(imagesPath)) {
                Files.createDirectories(imagesPath);
            }
        } catch (IOException e) {
            System.err.println("Error creating images directory: " + e.getMessage());
        }
    }
    
    private void setupPhotoClickListener() {
        jpicture.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectPhoto();
            }
        });
        
        jpicture.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jpicture.setToolTipText("Click to select photo");
    }
    
    private void setupPlaceholders() {
        setupPlaceholder(jTextField1, "Enter first name...");
        setupPlaceholder(jTextField2, "Enter last name...");
        setupPlaceholder(jTextField3, "Enter username...");
        setupPasswordPlaceholder();
    }
    
    private void setupPlaceholder(javax.swing.JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(java.awt.Color.GRAY);
        
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(java.awt.Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    }
    
    private void setupPasswordPlaceholder() {
        jPasswordField1.setText("password");
        jPasswordField1.setForeground(java.awt.Color.GRAY);
        jPasswordField1.setEchoChar((char) 0); // Show placeholder text
        
        jPasswordField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (String.valueOf(jPasswordField1.getPassword()).equals("password")) {
                    jPasswordField1.setText("");
                    jPasswordField1.setForeground(java.awt.Color.BLACK);
                    jPasswordField1.setEchoChar('*'); // Hide password
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jPasswordField1.getPassword().length == 0) {
                    jPasswordField1.setText("password");
                    jPasswordField1.setForeground(java.awt.Color.GRAY);
                    jPasswordField1.setEchoChar((char) 0); // Show placeholder text
                }
            }
        });
    }
    
    private void loadRoles() {
        if (roleDAO != null) {
            try {
                List<Role> roles = roleDAO.getAllRoles();
                jComboBox1.removeAllItems();
                for (Role role : roles) {
                    jComboBox1.addItem(role.getName());
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error loading roles: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void selectPhoto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Profile Photo");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        // Set file filter for images
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image files", "jpg", "jpeg", "png", "gif", "bmp");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Generate unique filename
                String filename = "user_" + System.currentTimeMillis() + 
                    getFileExtension(selectedFile.getName());
                
                // Copy file to images directory
                Path sourcePath = selectedFile.toPath();
                Path targetPath = Paths.get("src/main/resources/images", filename);
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                
                selectedPhotoPath = filename;
                
                // Display the image in the label
                displayPhoto(targetPath.toString());
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error copying photo: " + e.getMessage(),
                    "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        return (lastDotIndex > 0) ? filename.substring(lastDotIndex) : "";
    }
    
    private void displayPhoto(String imagePath) {
        try {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            // Scale the image to fit the label
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(
                jpicture.getWidth(), jpicture.getHeight(), Image.SCALE_SMOOTH);
            jpicture.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.err.println("Error displaying photo: " + e.getMessage());
        }
    }
    
    public void setUserData(UserAccount user) {
        currentUser = user;
        isEditMode = (user != null);
        
        if (isEditMode) {
            // Populate form with existing user data
            clearPlaceholders();
            jTextField1.setText(user.getFirstname() != null ? user.getFirstname() : "");
            jTextField2.setText(user.getLastname() != null ? user.getLastname() : "");
            jTextField3.setText(user.getUsername() != null ? user.getUsername() : "");
            jPasswordField1.setText(""); // Don't show existing password
            
            // Set role
            if (user.getRole() != null) {
                jComboBox1.setSelectedItem(user.getRole().getName());
            }
            
            // Load existing photo if available
            if (user.getPhoto() != null && user.getPhoto().length > 0) {
                try {
                    // Save byte[] data to temporary file for display
                    selectedPhotoPath = "temp_user_" + user.getId() + ".jpg";
                    Path tempPath = Paths.get("src/main/resources/images/", selectedPhotoPath);
                    Files.write(tempPath, user.getPhoto());
                    displayPhoto(tempPath.toString());
                } catch (IOException e) {
                    System.err.println("Error displaying existing photo: " + e.getMessage());
                }
            }
            
            jButton1.setText("Update");
        } else {
            // Clear form for new user
            clearForm();
            jButton1.setText("Save");
        }
    }
    
    private void clearPlaceholders() {
        if (jTextField1.getForeground().equals(java.awt.Color.GRAY)) {
            jTextField1.setText("");
            jTextField1.setForeground(java.awt.Color.BLACK);
        }
        if (jTextField2.getForeground().equals(java.awt.Color.GRAY)) {
            jTextField2.setText("");
            jTextField2.setForeground(java.awt.Color.BLACK);
        }
        if (jTextField3.getForeground().equals(java.awt.Color.GRAY)) {
            jTextField3.setText("");
            jTextField3.setForeground(java.awt.Color.BLACK);
        }
        if (jPasswordField1.getForeground().equals(java.awt.Color.GRAY)) {
            jPasswordField1.setText("");
            jPasswordField1.setForeground(java.awt.Color.BLACK);
            jPasswordField1.setEchoChar('*');
        }
    }
    
    private void clearForm() {
        setupPlaceholders();
        jComboBox1.setSelectedIndex(0);
        selectedPhotoPath = null;
        jpicture.setIcon(new ImageIcon(getClass().getResource("/images/no photo.jpg")));
    }
    
    private boolean validateForm() {
        String firstName = jTextField1.getText();
        String lastName = jTextField2.getText();
        String username = jTextField3.getText();
        String password = String.valueOf(jPasswordField1.getPassword());
        
        // Check for empty or placeholder values
        if (firstName.isEmpty() || firstName.equals("Enter first name...")) {
            showValidationError("Please enter a first name.");
            return false;
        }
        
        if (lastName.isEmpty() || lastName.equals("Enter last name...")) {
            showValidationError("Please enter a last name.");
            return false;
        }
        
        if (username.isEmpty() || username.equals("Enter username...")) {
            showValidationError("Please enter a username.");
            return false;
        }
        
        if (!isEditMode && (password.isEmpty() || password.equals("password"))) {
            showValidationError("Please enter a password.");
            return false;
        }
        
        return true;
    }
    
    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.WARNING_MESSAGE);
    }
    
    public void setRefreshCallback(Runnable callback) {
        this.refreshCallback = callback;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPasswordField1 = new javax.swing.JPasswordField();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jpicture = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("October Compressed Tamil", 1, 18)); // NOI18N
        jLabel1.setText("Firstname:");

        jLabel2.setFont(new java.awt.Font("October Compressed Tamil", 1, 18)); // NOI18N
        jLabel2.setText("Lastname:");

        jLabel3.setFont(new java.awt.Font("October Compressed Tamil", 1, 18)); // NOI18N
        jLabel3.setText("Role:");

        jLabel4.setFont(new java.awt.Font("October Compressed Tamil", 1, 18)); // NOI18N
        jLabel4.setText("Username:");

        jLabel5.setFont(new java.awt.Font("October Compressed Tamil", 1, 18)); // NOI18N
        jLabel5.setText("Password:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 2, true), "Photo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Helvetica Neue", 0, 13), new java.awt.Color(0, 102, 153))); // NOI18N

        jpicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/no photo.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpicture, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpicture, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(26, 26, 26)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(218, 218, 218)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!validateForm()) {
            return;
        }
        
        try {
            String firstName = jTextField1.getText();
            String lastName = jTextField2.getText();
            String username = jTextField3.getText();
            String password = String.valueOf(jPasswordField1.getPassword());
            String role = (String) jComboBox1.getSelectedItem();
            
            if (isEditMode) {
                // Update existing user
                currentUser.setFirstname(firstName);
                currentUser.setLastname(lastName);
                currentUser.setUsername(username);
                // Find and set the Role object
                if (roleDAO != null) {
                    try {
                        Role roleObj = roleDAO.getAllRoles().stream()
                            .filter(r -> r.getName().equals(role))
                            .findFirst()
                            .orElse(null);
                        currentUser.setRole(roleObj);
                    } catch (SQLException ex) {
                        System.err.println("Error setting role: " + ex.getMessage());
                    }
                }
                
                // Only update password if it's not the placeholder
                if (!password.equals("password") && !password.isEmpty()) {
                    currentUser.setPassword(password);
                }
                
                // Update photo if selected
                if (selectedPhotoPath != null) {
                    try {
                        Path photoPath = Paths.get("src/main/resources/images/" + selectedPhotoPath);
                        if (Files.exists(photoPath)) {
                            byte[] photoBytes = Files.readAllBytes(photoPath);
                            currentUser.setPhoto(photoBytes);
                        }
                    } catch (IOException ex) {
                        System.err.println("Error reading photo: " + ex.getMessage());
                    }
                }
                
                if (userAccountDAO != null) {
                    try {
                        userAccountDAO.updateUser(currentUser);
                        
                        JOptionPane.showMessageDialog(this, 
                            "User updated successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, 
                            "Error updating user: " + ex.getMessage(),
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                        return; // Don't close dialog on error
                    }
                }
            } else {
                // Create new user
                UserAccount newUser = new UserAccount();
                newUser.setFirstname(firstName);
                newUser.setLastname(lastName);
                newUser.setUsername(username);
                newUser.setPassword(password);
                // Find and set the Role object
                if (roleDAO != null) {
                    try {
                        Role roleObj = roleDAO.getAllRoles().stream()
                            .filter(r -> r.getName().equals(role))
                            .findFirst()
                            .orElse(null);
                        newUser.setRole(roleObj);
                    } catch (SQLException ex) {
                        System.err.println("Error setting role: " + ex.getMessage());
                    }
                }
                // Set photo if selected
                if (selectedPhotoPath != null) {
                    try {
                        Path photoPath = Paths.get("src/main/resources/images/" + selectedPhotoPath);
                        if (Files.exists(photoPath)) {
                            byte[] photoBytes = Files.readAllBytes(photoPath);
                            newUser.setPhoto(photoBytes);
                        }
                    } catch (IOException ex) {
                        System.err.println("Error reading photo: " + ex.getMessage());
                    }
                }
                
                if (userAccountDAO != null) {
                    try {
                        userAccountDAO.addUser(newUser);
                        
                        JOptionPane.showMessageDialog(this, 
                            "User created successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, 
                            "Error creating user: " + ex.getMessage(),
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                        return; // Don't close dialog on error
                    }
                }
            }
            
            dispose();
            
            // Call refresh callback if set
            if (refreshCallback != null) {
                refreshCallback.run();
            }
            
        } catch (Exception e) {
            String action = isEditMode ? "updating" : "creating";
            JOptionPane.showMessageDialog(this, 
                "Error " + action + " user: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserAccDialog_2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserAccDialog_2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserAccDialog_2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserAccDialog_2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserAccDialog_2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel jpicture;
    // End of variables declaration//GEN-END:variables
}
