package com.ecommerce_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce_backend.dto.OrderRequest;
import com.ecommerce_backend.entity.Orders;
import com.ecommerce_backend.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
	private OrderService service;
	
	public OrderController(OrderService service) {
		this.service =service;
		
	}
	 
	@GetMapping
	public List<Orders> getAllOrders(){
		return service.getAllOrders();
	}
	
	@GetMapping("/{id}")
	public Orders getOne(@PathVariable Long id) {
		return service.getById(id);
	}
	@PostMapping("/pay/{id}")
	public Orders makePayment(@PathVariable Long id) {
	    return service.makePayment(id);
	}
	@PostMapping("/place/{userId}")
	public Orders placeOrder(@PathVariable Long userId) {
	    return service.placeOrder(userId);
	}
	@GetMapping("/user/{userId}")
	public List<OrderRequest> getOrdersByUser(@PathVariable Long userId) {
	    return service.getOrdersByUser(userId);
	}
}

