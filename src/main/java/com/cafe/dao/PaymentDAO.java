/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cafe.dao;

import com.cafe.model.Payment;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author justlowkee
 */
public interface PaymentDAO {
    Payment createPayment(Payment payment) throws SQLException;
    Payment getPaymentById(Long id) throws SQLException;
    List<Payment> getPaymentsByOrderId(Long orderId) throws SQLException;
    Payment updatePaymentStatus(Long id, String status) throws SQLException;
    List<Payment> getAllPayments() throws SQLException;
}
