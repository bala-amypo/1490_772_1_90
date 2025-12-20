package com.example.demo.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // Create / Register User
    @PostMapping
    public User register(@Valid @RequestBody User user) {
        return service.registerUser(user);
    }

    // Get User by ID
    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return service.getUser(id);
    }

    // Get All Users
    @GetMapping
    public List<User> getAll() {
        return service.getAllUsers();
    }
}
