package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{
    boolean existsByEmail(String email);
}
