package com.cafe.gui;

import com.cafe.dao.MenuItemDAO;
import com.cafe.dao.MenuItemDAOImpl;
import com.cafe.dao.UserAccountDAOImpl;
import com.cafe.gui.dialogs.PaymentDialog;
import com.cafe.model.MenuItem;
import com.cafe.model.Payment;
import com.cafe.model.UserAccount;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;

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
    private MenuItemDAO menuItemDAO;
    private List<MenuItem> menuItems;
    private Map<String, List<MenuItem>> menuByCategory;
    private String selectedCategory = null;
    private DecimalFormat priceFormat = new DecimalFormat("$#0.00");
    
    public CafeMain() {
        initComponents();
        // Center the frame
        setLocationRelativeTo(null);
        
        // Initialize DAO and load menu data
        try {
            menuItemDAO = new MenuItemDAOImpl();
            setupOrderTable();
            loadMenuItems();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error initializing menu: " + e.getMessage(), 
                                        "Initialization Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setCurrentUser(UserAccount user) {
        this.currentUser = user;
        updateMenuText();
    }
    
    public void refreshMenu() {
        loadMenuItems();
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
    
    private void setupOrderTable() {
        // Setup the order table with proper columns
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Item", "Price", "Qty", "Total"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        jTable.setModel(model);
        
        // Apply FlatLaf table styling
        jTable.putClientProperty(FlatClientProperties.STYLE, "showHorizontalLines: true; showVerticalLines: true;");
        
        // Set column widths
        if (jTable.getColumnModel().getColumnCount() > 0) {
            jTable.getColumnModel().getColumn(0).setPreferredWidth(200); // Item
            jTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Price
            jTable.getColumnModel().getColumn(2).setPreferredWidth(50);  // Qty
            jTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Total
        }
    }
    
    private void loadMenuItems() {
        if (menuItemDAO == null) return;
        
        try {
            // Clear existing data first
            menuItems = null;
            menuByCategory = null;
            jPanelShowCategories.removeAll();
            jPanelShowMenu.removeAll();
            
            menuItems = menuItemDAO.getAvailableMenuItems();
            menuByCategory = new HashMap<>();
            
            // Group items by category
            for (MenuItem item : menuItems) {
                String category = item.getCategory();
                menuByCategory.computeIfAbsent(category, k -> new java.util.ArrayList<>()).add(item);
            }
            
            // Load categories
            loadCategories();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading menu items: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadCategories() {
        jPanelShowCategories.removeAll();
        
        if (menuByCategory == null || menuByCategory.isEmpty()) {
            return;
        }
        
        // Create category buttons
        for (String category : menuByCategory.keySet()) {
            JButton categoryButton = new JButton(category);
            categoryButton.setPreferredSize(new Dimension(120, 60));
            categoryButton.setBackground(new Color(0, 102, 153));
            categoryButton.setForeground(Color.WHITE);
            categoryButton.setFocusPainted(false);
            categoryButton.setBorder(BorderFactory.createRaisedBevelBorder());
            
            
            categoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedCategory = category;
                    loadMenuForCategory(category);
                    highlightSelectedCategory(categoryButton);
                }
            });
            
            jPanelShowCategories.add(categoryButton);
        }
        
        jPanelShowCategories.revalidate();
        jPanelShowCategories.repaint();
    }
    
    private void loadMenuForCategory(String category) {
        // Clear menu panel completely
        jPanelShowMenu.removeAll();
        jPanelShowMenu.revalidate();
        jPanelShowMenu.repaint();
        
        List<MenuItem> categoryItems = menuByCategory.get(category);
        if (categoryItems == null || categoryItems.isEmpty()) {
            jPanelShowMenu.revalidate();
            jPanelShowMenu.repaint();
            return;
        }
        
        // Create menu item buttons
        for (MenuItem item : categoryItems) {
            JButton itemButton = new JButton();
            itemButton.setText("<html><center>" + item.getName() + "<br>" + 
                             priceFormat.format(item.getPrice()) + "</center></html>");
            itemButton.setPreferredSize(new Dimension(120, 70));
            itemButton.setBackground(Color.WHITE);
            itemButton.setForeground(new Color(0, 102, 153));
            itemButton.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153), 2));
            itemButton.setFocusPainted(false);
            
            
            itemButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Visual feedback for button press
                    itemButton.setBackground(new Color(200, 230, 255));
                    javax.swing.Timer timer = new javax.swing.Timer(200, evt -> {
                        itemButton.setBackground(Color.WHITE);
                    });
                    timer.setRepeats(false);
                    timer.start();
                    
                    addItemToOrder(item);
                }
            });
            
            jPanelShowMenu.add(itemButton);
        }
        
        jPanelShowMenu.revalidate();
        jPanelShowMenu.repaint();
    }
    
    private void highlightSelectedCategory(JButton selectedButton) {
        // Reset all category buttons
        for (java.awt.Component comp : jPanelShowCategories.getComponents()) {
            if (comp instanceof JButton) {
                comp.setBackground(new Color(0, 102, 153));
            }
        }
        
        // Highlight selected category
        selectedButton.setBackground(new Color(0, 153, 204));
    }
    
    private void addItemToOrder(MenuItem item) {
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        
        // Check if item already exists in order
        boolean itemExists = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            String existingItem = (String) model.getValueAt(i, 0);
            if (existingItem.equals(item.getName())) {
                // Increase quantity
                int currentQty = (Integer) model.getValueAt(i, 2);
                int newQty = currentQty + 1;
                double total = item.getPrice() * newQty;
                
                model.setValueAt(newQty, i, 2);
                model.setValueAt(priceFormat.format(total), i, 3);
                itemExists = true;
                break;
            }
        }
        
        // Add new item if it doesn't exist
        if (!itemExists) {
            model.addRow(new Object[]{
                item.getName(),
                priceFormat.format(item.getPrice()),
                1,
                priceFormat.format(item.getPrice())
            });
        }
        
        updateOrderTotal();
    }
    
    private void updateOrderTotal() {
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        double total = 0.0;
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String totalStr = (String) model.getValueAt(i, 3);
            // Remove currency symbol and parse
            totalStr = totalStr.replace("$", "");
            total += Double.parseDouble(totalStr);
        }
        
        jLabelAmountDueTotal.setText(String.format("%.2f", total));
    }
    
    private void modifySelectedItemQuantity(int change) {
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item from the order.", 
                                        "No Item Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        int currentQty = (Integer) model.getValueAt(selectedRow, 2);
        int newQty = currentQty + change;
        
        if (newQty <= 0) {
            // Remove item if quantity becomes 0 or negative
            model.removeRow(selectedRow);
        } else {
            // Update quantity and total
            String priceStr = (String) model.getValueAt(selectedRow, 1);
            priceStr = priceStr.replace("$", "");
            double price = Double.parseDouble(priceStr);
            double newTotal = price * newQty;
            
            model.setValueAt(newQty, selectedRow, 2);
            model.setValueAt(priceFormat.format(newTotal), selectedRow, 3);
        }
        
        updateOrderTotal();
    }
    
    private void clearOrder() {
        // Clear the table model
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        model.setRowCount(0);
        
        // Reset totals
        jLabelAmountDueTotal.setText("0.00");
        jLabelCash.setText("0.00");
        jLabelChange.setText("0.00");
        
        // Clear any table selection
        jTable.clearSelection();
        
        // Refresh the table display
        jTable.revalidate();
        jTable.repaint();
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
        jPanel7 = new javax.swing.JPanel();
        jButtonMinus = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jButtonEditQty = new javax.swing.JButton();
        jButtonRemoveQty = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
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

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, java.awt.Color.gray));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Total:");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 180, 50));

        jLabelAmountDueTotal.setBackground(new java.awt.Color(51, 51, 51));
        jLabelAmountDueTotal.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabelAmountDueTotal.setForeground(new java.awt.Color(102, 102, 102));
        jLabelAmountDueTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelAmountDueTotal.setText("0.00");
        jLabelAmountDueTotal.setPreferredSize(new java.awt.Dimension(110, 50));
        jPanel2.add(jLabelAmountDueTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 130, 50));

        jLabel11.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Cash:");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel11.setPreferredSize(new java.awt.Dimension(110, 50));
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 180, 50));

        jLabelCash.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabelCash.setForeground(new java.awt.Color(102, 102, 102));
        jLabelCash.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelCash.setText("0.00");
        jLabelCash.setPreferredSize(new java.awt.Dimension(110, 50));
        jPanel2.add(jLabelCash, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, 120, 50));

        jLabel8.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Change:");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel8.setPreferredSize(new java.awt.Dimension(110, 50));
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 180, 50));

        jLabelChange.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabelChange.setForeground(new java.awt.Color(102, 102, 102));
        jLabelChange.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelChange.setText("0.00");
        jLabelChange.setPreferredSize(new java.awt.Dimension(110, 50));
        jPanel2.add(jLabelChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 120, 50));

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("$");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, -1, 50));

        jLabel2.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("$");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, -1, -1));

        jLabel3.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("$");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, -1, 50));

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
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jButtonNew, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonHold, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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

        jTable.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4))
                .addGap(16, 16, 16))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

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
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 661, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
        clearOrder();
    }//GEN-LAST:event_jButtonNewActionPerformed

    private void jButtonHoldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHoldActionPerformed

    }//GEN-LAST:event_jButtonHoldActionPerformed

    private void jButtonPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPaymentActionPerformed
        processPayment();
    }//GEN-LAST:event_jButtonPaymentActionPerformed
    
    private void processPayment() {
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, 
                "No items in order. Please add items before proceeding to payment.",
                "Empty Order", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            double totalAmount = Double.parseDouble(jLabelAmountDueTotal.getText());
            
            if (totalAmount <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "Invalid order total. Please check your order.",
                    "Invalid Total", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Long orderId = System.currentTimeMillis();
            
            PaymentDialog paymentDialog = new PaymentDialog(orderId, totalAmount, new PaymentDialog.PaymentCallback() {
                @Override
                public void onPaymentComplete(Payment processedPayment) {
                    // Clear the order first
                    clearOrder();
                    
                    // Show success message
                    String successMessage = String.format(
                        "Payment processed successfully!\n\n" +
                        "Transaction ID: %s\n" +
                        "Amount: $%.2f\n" +
                        "Method: %s\n\n" +
                        "Order has been cleared and ready for next customer.",
                        processedPayment.getTransactionId(),
                        processedPayment.getAmount(),
                        processedPayment.getPaymentMethod()
                    );
                    
                    JOptionPane.showMessageDialog(CafeMain.this, 
                        successMessage,
                        "Payment Complete", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
                
                @Override
                public void onPaymentCancelled() {
                    System.out.println("Payment was cancelled");
                }
            });
            
            paymentDialog.setVisible(true);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Error reading order total. Please try again.",
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Unexpected error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButtonMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMinusActionPerformed
        modifySelectedItemQuantity(-1);
    }//GEN-LAST:event_jButtonMinusActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        modifySelectedItemQuantity(1);
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonEditQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditQtyActionPerformed

    }//GEN-LAST:event_jButtonEditQtyActionPerformed

    private void jButtonRemoveQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveQtyActionPerformed
        int selectedRow = jTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.",
                "No Item Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        String itemName = (String) model.getValueAt(selectedRow, 0);

        int response = JOptionPane.showConfirmDialog(this,
            "Remove " + itemName + " from order?",
            "Confirm Remove",
            JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            model.removeRow(selectedRow);
            updateOrderTotal();
        }
    }//GEN-LAST:event_jButtonRemoveQtyActionPerformed

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
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    public javax.swing.JPanel jPanelShowCategories;
    public javax.swing.JPanel jPanelShowMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable;
    // End of variables declaration//GEN-END:variables

}
