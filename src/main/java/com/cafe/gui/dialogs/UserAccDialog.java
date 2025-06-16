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
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author 18011129 Lorenz Soriano & 21143576 Phoebe Cruz
 */

public class UserAccDialog extends javax.swing.JFrame {
    
    private UserAccountDAO userAccountDAO;
    private RoleDAO roleDAO;
    private DefaultTableModel tableModel;
    private List<UserAccount> allUsers;
    private UserAccDialog_2 userFormDialog;

    /**
     * Creates new form UserAccDialog
     */
    public UserAccDialog() {
        initComponents();
        initializeUserDialog();
        setupTable();
        setupSearchFunctionality();
        loadUsers();
    }
    
    private void initializeUserDialog() {
        userAccountDAO = new UserAccountDAOImpl();
        try {
            roleDAO = new RoleDAOImpl();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error initializing role DAO: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    private void setupTable() {
        // Set up table model with proper column names
        String[] columnNames = {"ID", "First Name", "Last Name", "Username", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        jTable2.setModel(tableModel);
        
        // Set column widths
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(120); // First Name
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(120); // Last Name
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(120); // Username
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(100); // Role
        
        // Set selection mode
        jTable2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void setupSearchFunctionality() {
        // Add placeholder text
        jTextField2.setText("Search by name or username...");
        jTextField2.setForeground(java.awt.Color.GRAY);
        
        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jTextField2.getText().equals("Search by name or username...")) {
                    jTextField2.setText("");
                    jTextField2.setForeground(java.awt.Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jTextField2.getText().isEmpty()) {
                    jTextField2.setText("Search by name or username...");
                    jTextField2.setForeground(java.awt.Color.GRAY);
                }
            }
        });
        
        // Add real-time search
        jTextField2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterUsers(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterUsers(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterUsers(); }
        });
    }
    
    private void loadUsers() {
        if (userAccountDAO != null) {
            try {
                allUsers = userAccountDAO.getAllUsers();
                refreshTable(allUsers);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error loading users: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void refreshTable(List<UserAccount> users) {
        tableModel.setRowCount(0);
        for (UserAccount user : users) {
            Object[] row = {
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getUsername(),
                user.getRole() != null ? user.getRole().getName() : "No Role"
            };
            tableModel.addRow(row);
        }
    }
    
    private void filterUsers() {
        String searchText = jTextField2.getText().toLowerCase();
        if (searchText.equals("search by name or username...")) {
            refreshTable(allUsers);
            return;
        }
        
        List<UserAccount> filteredUsers = allUsers.stream()
            .filter(user -> 
                user.getFirstname().toLowerCase().contains(searchText) ||
                user.getLastname().toLowerCase().contains(searchText) ||
                user.getUsername().toLowerCase().contains(searchText)
            )
            .toList();
        
        refreshTable(filteredUsers);
    }
    
    private UserAccount getSelectedUser() {
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a user from the table.",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        Long userId = (Long) tableModel.getValueAt(selectedRow, 0);
        return allUsers.stream()
            .filter(user -> user.getId().equals(userId))
            .findFirst()
            .orElse(null);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("User Account");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton4.setText("New");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Update");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Delete");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel2.setText("Search");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 153, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // New User
        openUserForm(null);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // Update User
        UserAccount selectedUser = getSelectedUser();
        if (selectedUser != null) {
            openUserForm(selectedUser);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // Delete User
        UserAccount selectedUser = getSelectedUser();
        if (selectedUser == null) return;
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete user '" + selectedUser.getUsername() + "'?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            userAccountDAO.deleteUser(selectedUser.getId().intValue());
            JOptionPane.showMessageDialog(this, 
                "User deleted successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            loadUsers(); // Refresh the table
        }
    }//GEN-LAST:event_jButton6ActionPerformed
    
    private void openUserForm(UserAccount user) {
        userFormDialog = new UserAccDialog_2();
        userFormDialog.setUserData(user);
        userFormDialog.setRefreshCallback(() -> loadUsers());
        userFormDialog.setVisible(true);
    }

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
            java.util.logging.Logger.getLogger(UserAccDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserAccDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserAccDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserAccDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserAccDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
