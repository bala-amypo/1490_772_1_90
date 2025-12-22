package com.example.demo.repository;

import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.DuplicateRule;

public interface DuplicateRuleRepository extends JpaRepository<DuplicateRule, Long> {

    Optional<DuplicateRule> findByRuleName(String ruleName);
}
