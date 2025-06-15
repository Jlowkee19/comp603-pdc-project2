/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cafe.model;

import java.time.LocalDateTime;

/**
 *
 * @author 18011129 Lorenz Soriano & 21143576 Phoebe Cruz
 */

public class Payment {
    private Long id;
    private Long orderId;
    private double amount;
    private String paymentMethod; // CASH, PAYWAVE / EFTPOS
    private String status; // PENDING, COMPLETED, FAILED, REFUNDED
    private LocalDateTime paymentDate;
    private String transactionId;
    private double changeAmount;
    
    public Payment() {
    }
    
    public Payment(Long orderId, double amount, String paymentMethod) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = "PENDING";
        this.paymentDate = LocalDateTime.now();
        this.changeAmount = 0.0;
        this.transactionId = "TXN" + System.currentTimeMillis(); // Generate default transaction ID
    }
    
    // Getters and Setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public Long getOrderId() { 
        return orderId; 
    }
    
    public void setOrderId(Long orderId) { 
        this.orderId = orderId; 
    }
    
    public double getAmount() { 
        return amount; 
    }
    
    public void setAmount(double amount) { 
        this.amount = amount; 
    }
    
    public String getPaymentMethod() { 
        return paymentMethod; 
    }
    
    public void setPaymentMethod(String paymentMethod) { 
        this.paymentMethod = paymentMethod; 
    }
    
    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }
    
    public LocalDateTime getPaymentDate() { 
        return paymentDate; 
    }
    
    public void setPaymentDate(LocalDateTime paymentDate) { 
        this.paymentDate = paymentDate; 
    }
    
    public String getTransactionId() { 
        return transactionId; 
    }
    
    public void setTransactionId(String transactionId) { 
        this.transactionId = transactionId; 
    }
    
    public double getChangeAmount() { 
        return changeAmount; 
    }
    
    public void setChangeAmount(double changeAmount) { 
        this.changeAmount = changeAmount; 
    }
    
    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                ", paymentDate=" + paymentDate +
                '}';
    }
}