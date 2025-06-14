/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cafe.dao;

import com.cafe.db.Database;
import com.cafe.model.Payment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author justlowkee
 */
public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public Payment createPayment(Payment payment) throws SQLException {
        // Ensure payments table exists
        createPaymentsTableIfNotExists();
        
        String sql = "INSERT INTO payments (order_id, amount, payment_method, status, payment_date, transaction_id, change_amount) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setLong(1, payment.getOrderId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getPaymentMethod());
            stmt.setString(4, payment.getStatus());
            stmt.setTimestamp(5, Timestamp.valueOf(payment.getPaymentDate()));
            stmt.setString(6, payment.getTransactionId());
            stmt.setDouble(7, payment.getChangeAmount());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        payment.setId(generatedKeys.getLong(1));
                    }
                }
            }
            
            return payment;
        }
    }

    @Override
    public Payment getPaymentById(Long id) throws SQLException {
        String sql = "SELECT * FROM payments WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createPaymentFromResultSet(rs);
                }
            }
        }
        
        return null;
    }

    @Override
    public List<Payment> getPaymentsByOrderId(Long orderId) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE order_id = ? ORDER BY payment_date DESC";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    payments.add(createPaymentFromResultSet(rs));
                }
            }
        }
        
        return payments;
    }

    @Override
    public Payment updatePaymentStatus(Long id, String status) throws SQLException {
        String sql = "UPDATE payments SET status = ? WHERE id = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setLong(2, id);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return getPaymentById(id);
            }
        }
        
        return null;
    }

    @Override
    public List<Payment> getAllPayments() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments ORDER BY payment_date DESC";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                payments.add(createPaymentFromResultSet(rs));
            }
        }
        
        return payments;
    }
    
    private Payment createPaymentFromResultSet(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getLong("id"));
        payment.setOrderId(rs.getLong("order_id"));
        payment.setAmount(rs.getDouble("amount"));
        payment.setPaymentMethod(rs.getString("payment_method"));
        payment.setStatus(rs.getString("status"));
        payment.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
        payment.setTransactionId(rs.getString("transaction_id"));
        payment.setChangeAmount(rs.getDouble("change_amount"));
        return payment;
    }
    
    private void createPaymentsTableIfNotExists() throws SQLException {
        String createTableSQL = "CREATE TABLE payments (" +
            "id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
            "order_id BIGINT, " +
            "amount DECIMAL(10,2) NOT NULL CHECK (amount >= 0), " +
            "payment_method VARCHAR(20) NOT NULL, " +
            "status VARCHAR(20) DEFAULT 'PENDING', " +
            "payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            "transaction_id VARCHAR(100), " +
            "change_amount DECIMAL(10,2) DEFAULT 0" +
            ")";
        
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            // Ignore "table already exists" error
            if (!e.getSQLState().equals("X0Y32")) {
                throw e;
            }
        }
    }
}
