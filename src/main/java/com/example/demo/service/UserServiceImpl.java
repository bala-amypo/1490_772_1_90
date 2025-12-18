package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserModel;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepo;
    @Override
    public UserModel registerUser(UserModel user){
        return userRepo.save(user);
    }
    @Override
    public UserModel getUser(Long id){
        return userRepo.findById(id).orElse(new UserModel());
    }
    @Override
    public List<UserModel>getAllUsers(){
        return userRepo.findAll();
    }
}
