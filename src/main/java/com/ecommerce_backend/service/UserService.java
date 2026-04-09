package com.ecommerce_backend.service;

import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }
    
    public User register(User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            throw new RuntimeException("Email and password required");
        }

        if (repo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        return repo.save(user);
    }

    public User login(User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            throw new RuntimeException("Email and password required");
        }

        Optional<User> existingUser = repo.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            User dbUser = existingUser.get();

            if (dbUser.getPassword().equals(user.getPassword())) {
                return dbUser;
            }
        }

        throw new RuntimeException("Invalid email or password");
    }
    public List<User> getAll() {
        return repo.findAll();
    }
}