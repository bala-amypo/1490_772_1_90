package com.example.demo.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.entity.UserModel;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private final UserRepository userRepo;
    
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    
    @Override
    @Transactional
    public UserModel registerUser(UserModel user) {
        logger.info("Registering user with email: {}", user.getEmail());
        
        validateUser(user);
        checkDuplicateEmail(user.getEmail());
        
        UserModel savedUser = userRepo.save(user);
        logger.info("User registered successfully with ID: {}", savedUser.getId());
        
        return savedUser;
    }
    
    @Override
    public UserModel getUser(Long id) {
        logger.debug("Fetching user with ID: {}", id);
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id));
    }
    
    @Override
    public List<UserModel> getAllUsers() {
        logger.debug("Fetching all users");
        return userRepo.findAll();
    }
    
    @Override
    @Transactional
    public UserModel updateUser(Long id, UserModel userDetails) {
        logger.info("Updating user with ID: {}", id);
        
        UserModel existingUser = getUser(id);
        
        if (userDetails.getName() != null) {
            existingUser.setName(userDetails.getName());
        }
        
        if (userDetails.getEmail() != null && 
            !userDetails.getEmail().equals(existingUser.getEmail())) {
            validateEmail(userDetails.getEmail());
            checkDuplicateEmail(userDetails.getEmail());
            existingUser.setEmail(userDetails.getEmail());
        }
        
        return userRepo.save(existingUser);
    }
    
    @Override
    @Transactional
    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        
        if (!userRepo.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        
        userRepo.deleteById(id);
        logger.info("User deleted successfully with ID: {}", id);
    }
    
    private void validateUser(UserModel user) {
        if (user == null) {
            throw new IllegalArgumentException("User data cannot be null");
        }
        
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        
        validateEmail(user.getEmail());
        
        if (user.getPassword() != null && user.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
    }
    
    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
    
    private void checkDuplicateEmail(String email) {
        if (userRepo.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
    }
}