package com.ecommerce_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce_backend.entity.Product;

public interface ProductRepositoty extends JpaRepository<Product, Long>{


	List<Product> findByCategoryId(Long categoryId);
}
