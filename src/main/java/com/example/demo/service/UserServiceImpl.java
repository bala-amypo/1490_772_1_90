package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User registerUser(User user) {
        if (repo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("email already exists");
        }
        return repo.save(user);
    }

    @Override
    public User getUser(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }
}
