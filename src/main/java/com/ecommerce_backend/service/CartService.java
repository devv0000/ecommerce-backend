package com.ecommerce_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce_backend.dto.CartRequest;
import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.Product;
import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.repository.CartRepository;
import com.ecommerce_backend.repository.ProductRepositoty;
import com.ecommerce_backend.repository.UserRepository;



@Service
public class CartService {

	private CartRepository cartRepo;
	private ProductRepositoty productRepo;
	private UserRepository userRepo;
	
	public CartService(CartRepository cartRepo ,ProductRepositoty productRepo, UserRepository userRepo) {
		this.cartRepo = cartRepo;
		this.productRepo = productRepo;
		this.userRepo = userRepo;
	}
	public Cart addToCart(CartRequest request) {

	    Product product = productRepo.findById(request.getProductId())
	        .orElseThrow(() -> new RuntimeException("Product not found"));
	    
	    User user = userRepo.findById(request.getUserId())
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    Cart cart = new Cart();
	    cart.setProduct(product);
	    cart.setQuantity(request.getQuantity());
	    cart.setUser(user); 
	    cart.setTotalPrice(product.getPrice() * request.getQuantity());
	    
	    return cartRepo.save(cart);
		
	}
	
	public List<Cart> getAll(){
		return cartRepo.findAll();
	}
	
	public Cart updateCart(Long id, Cart cart) {

	    Cart existing = cartRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Cart not found"));

	    existing.setQuantity(cart.getQuantity());

	    double total = existing.getProduct().getPrice() * cart.getQuantity();
	    existing.setTotalPrice(total);

	    return cartRepo.save(existing);
	}
	// ✅ Increase Quantity
	public Cart increaseQuantity(Long id) {

	    Cart cart = cartRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Cart not found"));

	    cart.setQuantity(cart.getQuantity() + 1);

	    // 🔥 IMPORTANT: total update 
	    double total = cart.getProduct().getPrice() * cart.getQuantity();
	    cart.setTotalPrice(total);

	    return cartRepo.save(cart);
	}

	// ✅ Decrease Quantity
	public Cart decreaseQuantity(Long id) {

	    Cart cart = cartRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Cart not found"));

	    if (cart.getQuantity() > 1) {
	        cart.setQuantity(cart.getQuantity() - 1);
	    }

	    // 🔥 IMPORTANT: total update 
	    double total = cart.getProduct().getPrice() * cart.getQuantity();
	    cart.setTotalPrice(total);

	    return cartRepo.save(cart);
	}
	
	public void delete(Long id) {
	  cartRepo.deleteById(id);
	}
	
	public double getgrandTotal() {
		List<Cart> list = cartRepo.findAll();
		double total = 0;
		
		for(Cart c : list) {
			total += c.getTotalPrice();
		}
		return total;
	}
}