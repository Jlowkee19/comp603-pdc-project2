package com.cafe.gui;

import com.cafe.dao.UserAccountDAOImpl;
import com.cafe.model.UserAccount;
import java.awt.GridLayout;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Enzo
 */
public class CafeMain extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CafeMain.class.getName());
    private UserAccount currentUser;
    private Runnable logoutCallback;
    
    public CafeMain() {
        initComponents();
        // Center the frame
        setLocationRelativeTo(null);
    }
    
    public void setCurrentUser(UserAccount user) {
        this.currentUser = user;
        updateMenuText();
    }
    
    public void setLogoutCallback(Runnable callback){
        this.logoutCallback = callback;
    }
    
    private void updateMenuText() {
        if (currentUser != null) {
            String displayName = currentUser.getFirstname() + " " + currentUser.getLastname();
            jMenuUser.setText(displayName);
        } else {
            jMenuUser.setText("User");
        }
    }
    
    private void logout() {
        
        int confirm = JOptionPane.showConfirmDialog(
                this, 
                "Are you sure you want to log out?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
                
            if (confirm == JOptionPane.YES_OPTION) {
                // Clear current user
                currentUser = null;
                updateMenuText();

                // Hide main window
                this.setVisible(false);

                // Call the logout callback if available
                if (logoutCallback != null) {
                    logoutCallback.run();
                }
            }
    }
    
    private void openChangePasswordDialog() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "No user is currently logged in.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create password fields
        JPasswordField currentPasswordField = new JPasswordField(20);
        JPasswordField newPasswordField = new JPasswordField(20);
        JPasswordField confirmPasswordField = new JPasswordField(20);
        
        // Create panel for dialog
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Current Password:"));
        panel.add(currentPasswordField);
        panel.add(new JLabel("New Password:"));
        panel.add(newPasswordField);
        panel.add(new JLabel("Confirm New Password:"));
        panel.add(confirmPasswordField);
        
        int option = JOptionPane.showConfirmDialog(
            this, 
            panel, 
            "Change Password", 
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (option == JOptionPane.OK_OPTION) {
            String currentPassword = new String(currentPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            // Validate inputs
            if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!currentPassword.equals(currentUser.getPassword())) {
                JOptionPane.showMessageDialog(this, "Current password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (newPassword.length() < 6) {
                JOptionPane.showMessageDialog(this, "New password must be at least 6 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update password
            try {
                UserAccountDAOImpl userDAO = new UserAccountDAOImpl();
                currentUser.setPassword(newPassword);
                userDAO.updateUser(currentUser);
                
                JOptionPane.showMessageDialog(this, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Clear password fields for security
                currentPasswordField.setText("");
                newPasswordField.setText("");
                confirmPasswordField.setText("");
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Failed to update password: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelShowCategories = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanelShowMenu = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jButtonMinus = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jButtonEditQty = new javax.swing.JButton();
        jButtonRemoveQty = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabelAmountDueTotal = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabelCash = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabelChange = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButtonNew = new javax.swing.JButton();
        jButtonHold = new javax.swing.JButton();
        jButtonPayment = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuUser = new javax.swing.JMenu();
        jMenuItemLogOut = new javax.swing.JMenuItem();
        jMenuItemChangePassword = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuAdministration = new javax.swing.JMenu();
        jMenuItemUser = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 1, true), "Food Categories", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Courier New", 1, 24), new java.awt.Color(0, 102, 153))); // NOI18N
        jPanel5.setMaximumSize(new java.awt.Dimension(602, 202));
        jPanel5.setMinimumSize(new java.awt.Dimension(602, 202));
        jPanel5.setPreferredSize(new java.awt.Dimension(602, 250));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(30, 23));

        jPanelShowCategories.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanelShowCategories.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));
        jScrollPane1.setViewportView(jPanelShowCategories);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 153), 1, true), "Menu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Courier New", 0, 24), new java.awt.Color(0, 102, 153))); // NOI18N
        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanelShowMenu.setPreferredSize(new java.awt.Dimension(594, 1000));
        jPanelShowMenu.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 10));
        jScrollPane3.setViewportView(jPanelShowMenu);

        jPanel12.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 102, 153)), "Orders", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Courier New", 0, 24), new java.awt.Color(0, 102, 153))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(464, 400));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setMinimumSize(new java.awt.Dimension(60, 76));
        jTable1.setPreferredSize(new java.awt.Dimension(294, 80));
        jScrollPane2.setViewportView(jTable1);

        jPanel21.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 11, 520, 400));

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonMinus.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButtonMinus.setText("-");
        jButtonMinus.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButtonMinus.setPreferredSize(new java.awt.Dimension(65, 50));
        jButtonMinus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMinusActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonMinus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 25));

        jButtonAdd.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButtonAdd.setText("+");
        jButtonAdd.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButtonAdd.setPreferredSize(new java.awt.Dimension(65, 50));
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 50, 25));

        jButtonEditQty.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonEditQty.setText("Edit");
        jButtonEditQty.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButtonEditQty.setPreferredSize(new java.awt.Dimension(65, 50));
        jButtonEditQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditQtyActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonEditQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 70, 25));

        jButtonRemoveQty.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButtonRemoveQty.setText("Remove");
        jButtonRemoveQty.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButtonRemoveQty.setPreferredSize(new java.awt.Dimension(65, 50));
        jButtonRemoveQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveQtyActionPerformed(evt);
            }
        });
        jPanel7.add(jButtonRemoveQty, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 90, 25));

        jPanel21.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 415, 520, 30));

        jPanel10.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -6, 520, 440));

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, java.awt.Color.gray));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Total:");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 200, 50));

        jLabelAmountDueTotal.setBackground(new java.awt.Color(51, 51, 51));
        jLabelAmountDueTotal.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabelAmountDueTotal.setForeground(new java.awt.Color(102, 102, 102));
        jLabelAmountDueTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelAmountDueTotal.setText("0.00");
        jLabelAmountDueTotal.setPreferredSize(new java.awt.Dimension(110, 50));
        jPanel2.add(jLabelAmountDueTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 90, 50));

        jLabel11.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Cash:");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel11.setPreferredSize(new java.awt.Dimension(110, 50));
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 200, 40));

        jLabelCash.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabelCash.setForeground(new java.awt.Color(102, 102, 102));
        jLabelCash.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCash.setText("0.00");
        jLabelCash.setPreferredSize(new java.awt.Dimension(110, 50));
        jPanel2.add(jLabelCash, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, 90, 40));

        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Change:");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel8.setPreferredSize(new java.awt.Dimension(110, 50));
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 200, 40));

        jLabelChange.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabelChange.setForeground(new java.awt.Color(102, 102, 102));
        jLabelChange.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelChange.setText("0.00");
        jLabelChange.setPreferredSize(new java.awt.Dimension(110, 50));
        jPanel2.add(jLabelChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, 90, 40));

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("$");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, -1, 40));

        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("$");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("$");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, -1, 40));

        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, java.awt.Color.gray));

        jButtonNew.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jButtonNew.setForeground(new java.awt.Color(0, 102, 153));
        jButtonNew.setMnemonic('N');
        jButtonNew.setText("New");
        jButtonNew.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButtonNew.setPreferredSize(new java.awt.Dimension(150, 50));
        jButtonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewActionPerformed(evt);
            }
        });

        jButtonHold.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jButtonHold.setForeground(new java.awt.Color(0, 102, 153));
        jButtonHold.setMnemonic('H');
        jButtonHold.setText("Hold");
        jButtonHold.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButtonHold.setPreferredSize(new java.awt.Dimension(150, 50));
        jButtonHold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHoldActionPerformed(evt);
            }
        });

        jButtonPayment.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jButtonPayment.setForeground(new java.awt.Color(0, 102, 153));
        jButtonPayment.setMnemonic('P');
        jButtonPayment.setText("Pay");
        jButtonPayment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonPayment.setPreferredSize(new java.awt.Dimension(150, 50));
        jButtonPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPaymentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonNew, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                    .addComponent(jButtonHold, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButtonPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(jButtonNew, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonHold, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel13.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 530, 620));

        jPanel1.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 30, 529, 620));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 19, Short.MAX_VALUE))
        );

        jMenuBar1.setPreferredSize(new java.awt.Dimension(600, 30));

        jMenuUser.setText("User");
        jMenuUser.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenuUser.setOpaque(true);
        jMenuUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuUserActionPerformed(evt);
            }
        });

        jMenuItemLogOut.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenuItemLogOut.setText("Logout");
        jMenuItemLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLogOutActionPerformed(evt);
            }
        });
        jMenuUser.add(jMenuItemLogOut);

        jMenuItemChangePassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenuItemChangePassword.setText("Change Password");
        jMenuItemChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemChangePasswordActionPerformed(evt);
            }
        });
        jMenuUser.add(jMenuItemChangePassword);
        jMenuUser.add(jSeparator1);

        jMenuItemExit.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuUser.add(jMenuItemExit);

        jMenuBar1.add(jMenuUser);

        jMenuAdministration.setText("Setup");
        jMenuAdministration.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jMenuItemUser.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenuItemUser.setText("User");
        jMenuItemUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemUserActionPerformed(evt);
            }
        });
        jMenuAdministration.add(jMenuItemUser);

        jMenuBar1.add(jMenuAdministration);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
 
    private void jMenuItemLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLogOutActionPerformed
        logout();
    }//GEN-LAST:event_jMenuItemLogOutActionPerformed

    private void jMenuItemChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemChangePasswordActionPerformed
        openChangePasswordDialog();
    }//GEN-LAST:event_jMenuItemChangePasswordActionPerformed

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed

    }//GEN-LAST:event_jMenuItemExitActionPerformed

    private void jMenuUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuUserActionPerformed

    private void jMenuItemUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemUserActionPerformed

    }//GEN-LAST:event_jMenuItemUserActionPerformed

    private void jButtonNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewActionPerformed
       
    }//GEN-LAST:event_jButtonNewActionPerformed

    private void jButtonHoldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHoldActionPerformed

    }//GEN-LAST:event_jButtonHoldActionPerformed

    private void jButtonPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPaymentActionPerformed
       
    }//GEN-LAST:event_jButtonPaymentActionPerformed

    private void jButtonRemoveQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveQtyActionPerformed
       
    }//GEN-LAST:event_jButtonRemoveQtyActionPerformed

    private void jButtonEditQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditQtyActionPerformed

    }//GEN-LAST:event_jButtonEditQtyActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
       
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMinusActionPerformed
        
    }//GEN-LAST:event_jButtonMinusActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new CafeMain().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonEditQty;
    private javax.swing.JButton jButtonHold;
    private javax.swing.JButton jButtonMinus;
    private javax.swing.JButton jButtonNew;
    private javax.swing.JButton jButtonPayment;
    private javax.swing.JButton jButtonRemoveQty;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelAmountDueTotal;
    private javax.swing.JLabel jLabelCash;
    private javax.swing.JLabel jLabelChange;
    private javax.swing.JMenu jMenuAdministration;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemChangePassword;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemLogOut;
    private javax.swing.JMenuItem jMenuItemUser;
    private javax.swing.JMenu jMenuUser;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    public javax.swing.JPanel jPanelShowCategories;
    public javax.swing.JPanel jPanelShowMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
