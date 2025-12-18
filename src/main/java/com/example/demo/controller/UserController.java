package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserModel;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/add")
    public UserModel add(@RequestBody UserModel user){
        return userService.createUser(user);
    }
    @GetMapping("/all")
    public List<UserModel> show(){
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public UserModel getUserById(@PathVariable Long id){
        return userService.getUserById
    } 
}