package com.ecommerce_backend.controller;

import com.ecommerce_backend.dto.AuthResponse;
import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.security.JwtUtil;
import com.ecommerce_backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User savedUser = service.register(user);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User loggedInUser = service.login(user);

            String token = jwtUtil.generateToken(loggedInUser.getEmail());

            return ResponseEntity.ok(
                new AuthResponse(token, loggedInUser)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public List<User> getUsers() {
        return service.getAll();
    }
}