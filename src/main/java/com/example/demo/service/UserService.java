package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.UserModel;

public interface UserService {
    UserModel createUser(UserModel user);
    List<UserModel> getAllUsers();
}
