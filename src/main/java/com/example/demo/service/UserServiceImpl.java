package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.UserModel;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserModel registerUser(UserModel user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Duplicate email");
        }
        return userRepo.save(user);
    }

    @Override
    public UserModel getUser(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserModel> getAllUsers() {
        return userRepo.findAll();
    }
}



