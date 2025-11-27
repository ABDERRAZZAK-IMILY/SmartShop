package com.microtech.smartshop.repository;

import com.microtech.smartshop.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

     List<Payment> findByOrderId(Long Id);

}