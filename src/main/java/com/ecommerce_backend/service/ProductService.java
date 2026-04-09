package com.ecommerce_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce_backend.entity.Product;
import com.ecommerce_backend.entity.Category;
import com.ecommerce_backend.repository.ProductRepositoty;
import com.ecommerce_backend.repository.CategoryRepository;

@Service
public class ProductService {

    private ProductRepositoty repo;
    private CategoryRepository categoryRepo;

    // 🔥 constructor must be public
    public ProductService(ProductRepositoty repo, CategoryRepository categoryRepo) {
        this.repo = repo;
        this.categoryRepo = categoryRepo;
    }

    
    public Product save(Product product) {

        // 🔥 category fetch from DB
        Long categoryId = product.getCategory().getId();
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);

        return repo.save(product);
    }
    public List<Product> getByCategory(Long categoryId) {
        return repo.findByCategoryId(categoryId);
    }

    public List<Product> getAll() {
        return repo.findAll();
    }
}