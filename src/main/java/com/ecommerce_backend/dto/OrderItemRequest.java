package com.ecommerce_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {

	private Long productId;
    private String productName;
    private int quantity;
    private double price;
    private double total;
}
