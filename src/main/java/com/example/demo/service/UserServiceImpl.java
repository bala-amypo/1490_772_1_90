package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.UserModel;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public UserModel registerUser(UserModel user) {

        if (user == null) {
            throw new IllegalArgumentException("User data cannot be null");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (userRepo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Duplicate email");
        }

        return userRepo.save(user);
    }

    @Override
    public UserModel getUser(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id));
    }

    @Override
    public List<UserModel> getAllUsers() {
        return userRepo.findAll();
    }
}
