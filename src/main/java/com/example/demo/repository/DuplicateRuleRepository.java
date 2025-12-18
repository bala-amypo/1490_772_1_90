package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.DuplicateRuleModel;

public interface DuplicateRuleRepository
        extends JpaRepository<DuplicateRuleModel, Long> {
}
