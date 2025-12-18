package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.UserModel;

public interface UserService {
    UserModel registerUser(UserModel user);
    UserModel getUser(Long id);
    List<UserModel> getAllUsers();
}
