package com.ecommerce_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce_backend.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long>{
	List<Orders> findByUserId(Long userId);
	
	int countByUserId(Long userId);
}
