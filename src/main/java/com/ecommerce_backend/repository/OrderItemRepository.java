package com.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce_backend.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	
}
