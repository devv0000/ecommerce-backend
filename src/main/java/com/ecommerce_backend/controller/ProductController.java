package com.ecommerce_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce_backend.entity.Product;
import com.ecommerce_backend.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

	private ProductService service;
	
	public ProductController(ProductService service) {
		this.service =service;
	}
	@PostMapping
	public Product add(@RequestBody Product product) {
		return service.save(product);
	}
	@GetMapping("/category/{id}")
	public List<Product> getByCategory(@PathVariable Long id) {
	    return service.getByCategory(id);
	}
	
	@GetMapping()
	public List<Product> getAll(){
		return service.getAll();
	}
}

