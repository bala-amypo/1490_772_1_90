package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
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
        if (repo.existsByEmail(user.getEmail())) return null;
        return repo.save(user);
    }

    @Override
    public User getUser(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }
}
