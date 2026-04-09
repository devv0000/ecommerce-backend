package com.ecommerce_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce_backend.entity.Category;
import com.ecommerce_backend.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private CategoryService service;
	
	private CategoryController(CategoryService service) {
		this.service = service;
	}
	 @PostMapping
	    public Category add(@RequestBody Category category) {
	        System.out.println("HIT 🔥 " + category.getName());
	        return service.save(category);
	}
	@GetMapping 
	public List<Category> getAll(){
		return service.getAll();
	}
}
