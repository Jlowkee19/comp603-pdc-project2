/*
 * Simple test class to verify PaymentDialog functionality
 */
package com.cafe.test;

import com.cafe.gui.dialogs.PaymentDialog;
import javax.swing.SwingUtilities;

/**
 * Test class for PaymentDialog functionality
 * @author justlowkee
 */
public class PaymentDialogTest {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Test PaymentDialog with sample order
                Long orderId = 123L;
                double totalAmount = 25.50;
                
                PaymentDialog dialog = new PaymentDialog(orderId, totalAmount);
                dialog.setVisible(true);
                
                // Keep the application running until dialog is closed
                dialog.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                
            } catch (Exception e) {
                System.err.println("Error testing PaymentDialog: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}