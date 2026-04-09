package com.ecommerce_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce_backend.entity.Category;
import com.ecommerce_backend.repository.CategoryRepository;

@Service
public class CategoryService {
	
	private CategoryRepository repo;
	
	private CategoryService(CategoryRepository repo) {
		this.repo = repo;
	}
	
	public Category save(Category category) {
		return repo.save(category);
	}
	
	public List<Category> getAll(){
		return repo.findAll();
	}
}
