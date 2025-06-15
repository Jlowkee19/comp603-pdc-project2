/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cafe.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author 18011129 Lorenz Soriano & 21143576 Phoebe Cruz
 */

public class Receipt {
    private String receiptNumber;
    private Long orderId;
    private LocalDateTime dateTime;
    private List<ReceiptItem> items;
    private double subtotal;
    private double tax;
    private double total;
    private String paymentMethod;
    private double amountPaid;
    private double changeGiven;
    private String transactionId;
    private String cashierName;
    
    public static class ReceiptItem {
        private String name;
        private int quantity;
        private double unitPrice;
        private double totalPrice;
        
        public ReceiptItem(String name, int quantity, double unitPrice) {
            this.name = name;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.totalPrice = quantity * unitPrice;
        }
        
        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { 
            this.quantity = quantity;
            this.totalPrice = quantity * unitPrice;
        }
        
        public double getUnitPrice() { return unitPrice; }
        public void setUnitPrice(double unitPrice) { 
            this.unitPrice = unitPrice;
            this.totalPrice = quantity * unitPrice;
        }
        
        public double getTotalPrice() { return totalPrice; }
    }
    
    public Receipt() {
        this.dateTime = LocalDateTime.now();
        this.receiptNumber = "R" + System.currentTimeMillis();
    }
    
    public Receipt(Long orderId, List<ReceiptItem> items, Payment payment) {
        this();
        this.orderId = orderId;
        this.items = items;
        this.paymentMethod = payment.getPaymentMethod();
        this.transactionId = payment.getTransactionId();
        this.changeGiven = payment.getChangeAmount();
        
        calculateTotals();
        this.amountPaid = this.total + this.changeGiven;
    }
    
    private void calculateTotals() {
        this.subtotal = items.stream().mapToDouble(ReceiptItem::getTotalPrice).sum();
        this.tax = subtotal * 0.15; // 15% GST
        this.total = subtotal + tax;
    }
    
    // Getters and setters
    public String getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(String receiptNumber) { this.receiptNumber = receiptNumber; }
    
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    
    public List<ReceiptItem> getItems() { return items; }
    public void setItems(List<ReceiptItem> items) { 
        this.items = items;
        calculateTotals();
    }
    
    public double getSubtotal() { return subtotal; }
    public double getTax() { return tax; }
    public double getTotal() { return total; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }
    
    public double getChangeGiven() { return changeGiven; }
    public void setChangeGiven(double changeGiven) { this.changeGiven = changeGiven; }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public String getCashierName() { return cashierName; }
    public void setCashierName(String cashierName) { this.cashierName = cashierName; }
}