package com.ecommerce_backend.service;


import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ecommerce_backend.entity.Payment;
import com.ecommerce_backend.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository repo;

    public PaymentService(PaymentRepository repo) {
        this.repo = repo;
    }

    public Payment savePayment(Payment payment) {
        payment.setCreatedAt(LocalDateTime.now());
        return repo.save(payment);
    }
}