package com.ecommerce_backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
	
	private Long orderId;
    private double totalAmount;
    
    private int userOrderNumber;
    
    private List<OrderItemRequest> items;
    private LocalDateTime createdAt;

}
