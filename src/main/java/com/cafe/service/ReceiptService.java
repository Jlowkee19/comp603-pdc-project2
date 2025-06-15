/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cafe.service;

import com.cafe.model.Receipt;
import com.cafe.model.Receipt.ReceiptItem;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author 18011129 Lorenz Soriano & 21143576 Phoebe Cruz
 */

public class ReceiptService {
    
    private static final String RECEIPTS_DIR = "receipts";
    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("$#,##0.00");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    public ReceiptService() {
        createReceiptsDirectory();
    }
    
    private void createReceiptsDirectory() {
        File dir = new File(RECEIPTS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * Generate a formatted receipt string
     */
    public String generateReceiptText(Receipt receipt) {
        StringBuilder sb = new StringBuilder();
        
        // Header
        sb.append("=====================================\n");
        sb.append("           CAFE RECEIPT\n");
        sb.append("=====================================\n");
        sb.append("Receipt #: ").append(receipt.getReceiptNumber()).append("\n");
        sb.append("Date: ").append(receipt.getDateTime().format(DATE_FORMAT)).append("\n");
        sb.append("Order ID: ").append(receipt.getOrderId()).append("\n");
        if (receipt.getCashierName() != null) {
            sb.append("Cashier: ").append(receipt.getCashierName()).append("\n");
        }
        sb.append("-------------------------------------\n");
        
        // Items
        sb.append("ITEMS:\n");
        sb.append("-------------------------------------\n");
        for (ReceiptItem item : receipt.getItems()) {
            sb.append(String.format("%-20s %2dx %8s = %8s\n", 
                truncateString(item.getName(), 20),
                item.getQuantity(),
                CURRENCY_FORMAT.format(item.getUnitPrice()),
                CURRENCY_FORMAT.format(item.getTotalPrice())
            ));
        }
        sb.append("-------------------------------------\n");
        
        // Totals
        sb.append(String.format("%-30s %8s\n", "Subtotal:", CURRENCY_FORMAT.format(receipt.getSubtotal())));
        sb.append(String.format("%-30s %8s\n", "GST (15%):", CURRENCY_FORMAT.format(receipt.getTax())));
        sb.append("-------------------------------------\n");
        sb.append(String.format("%-30s %8s\n", "TOTAL:", CURRENCY_FORMAT.format(receipt.getTotal())));
        sb.append("=====================================\n");
        
        // Payment details
        sb.append("PAYMENT DETAILS:\n");
        sb.append("-------------------------------------\n");
        sb.append(String.format("%-30s %8s\n", "Method:", receipt.getPaymentMethod()));
        sb.append(String.format("%-30s %8s\n", "Amount Paid:", CURRENCY_FORMAT.format(receipt.getAmountPaid())));
        if (receipt.getChangeGiven() > 0) {
            sb.append(String.format("%-30s %8s\n", "Change:", CURRENCY_FORMAT.format(receipt.getChangeGiven())));
        }
        sb.append(String.format("%-30s %8s\n", "Transaction ID:", receipt.getTransactionId()));
        sb.append("=====================================\n");
        
        // Footer
        sb.append("        Thank you for your visit!\n");
        sb.append("         Please come again!\n");
        sb.append("=====================================\n");
        
        return sb.toString();
    }
    
    /**
     * Save receipt to file
     */
    public File saveReceiptToFile(Receipt receipt) throws IOException {
        String filename = String.format("%s/receipt_%s_%s.txt", 
            RECEIPTS_DIR, 
            receipt.getReceiptNumber(),
            receipt.getDateTime().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
        );
        
        File file = new File(filename);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(generateReceiptText(receipt));
        }
        
        return file;
    }
    
    /**
     * Print receipt using system printer
     */
    public void printReceipt(Receipt receipt) {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(new ReceiptPrintable(receipt));
        
        if (printerJob.printDialog()) {
            try {
                printerJob.print();
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, 
                    "Error printing receipt: " + e.getMessage(),
                    "Print Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Save receipt and open the file
     */
    public void saveAndOpenReceipt(Receipt receipt) {
        try {
            File receiptFile = saveReceiptToFile(receipt);
            
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(receiptFile);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Receipt saved to: " + receiptFile.getAbsolutePath(),
                    "Receipt Saved", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "Error saving receipt: " + e.getMessage(),
                "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Show receipt options dialog
     */
    public void showReceiptOptions(Receipt receipt) {
        String[] options = {"Print Receipt", "Save Receipt", "Print & Save", "Cancel"};
        int choice = JOptionPane.showOptionDialog(null,
            "What would you like to do with the receipt?",
            "Receipt Options",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[2] // Default to "Print & Save"
        );
        
        switch (choice) {
            case 0: // Print only
                printReceipt(receipt);
                break;
            case 1: // Save only
                saveAndOpenReceipt(receipt);
                break;
            case 2: // Print & Save
                printReceipt(receipt);
                saveAndOpenReceipt(receipt);
                break;
            default: // Cancel
                break;
        }
    }
    
    private String truncateString(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
    
    /**
     * Inner class for handling receipt printing
     */
    private class ReceiptPrintable implements Printable {
        private Receipt receipt;
        private String receiptText;
        
        public ReceiptPrintable(Receipt receipt) {
            this.receipt = receipt;
            this.receiptText = generateReceiptText(receipt);
        }
        
        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }
            
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            
            // Set font
            Font font = new Font("Courier New", Font.PLAIN, 10);
            g2d.setFont(font);
            
            // Print receipt text line by line
            String[] lines = receiptText.split("\n");
            int y = 15;
            int lineHeight = 12;
            
            for (String line : lines) {
                g2d.drawString(line, 10, y);
                y += lineHeight;
            }
            
            return PAGE_EXISTS;
        }
    }
}