package com.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce_backend.entity.Payment;



public interface PaymentRepository extends JpaRepository<Payment, Long> {
}