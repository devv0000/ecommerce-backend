package com.ecommerce_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce_backend.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	List<Cart> findByUserId(Long userId);

}
