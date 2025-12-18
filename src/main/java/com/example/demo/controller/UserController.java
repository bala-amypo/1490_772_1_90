package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserModel;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserModel register(@RequestBody UserModel user) {
        return userService.registerUser(user);
    }

    @GetMapping("/all")
    public List<UserModel> show() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserModel get(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
