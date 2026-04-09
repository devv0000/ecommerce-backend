package com.ecommerce_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce_backend.dto.CartRequest;
import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
	private CartService service;
	
	public CartController(CartService service) {
		this.service = service;
	}
	@PostMapping
	public Cart add(@RequestBody CartRequest request) {
	    return service.addToCart(request);
	}
	@GetMapping
	public List<Cart> getAll(){
		return service.getAll();
	}
	@PutMapping("/{id}")
	public Cart update(@PathVariable Long id ,@RequestBody Cart cart) {
		return service.updateCart(id, cart);
	}
	 @PutMapping("/increase/{id}")
	    public Cart increaseQty(@PathVariable Long id) {
	        return service.increaseQuantity(id);
	}

	    // ✅ NEW: Decrease Quantity
	@PutMapping("/decrease/{id}")
	    public Cart decreaseQty(@PathVariable Long id) {
	        return service.decreaseQuantity(id);
	}
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		service.delete(id);
		return "Deleted succesfully";
	}
	@GetMapping("/total")
	public double total() {
		return service.getgrandTotal();
	}
}
