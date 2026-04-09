package com.ecommerce_backend.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long productId;
	 
	    private int quantity;
	    
	    private Long userId;
}
