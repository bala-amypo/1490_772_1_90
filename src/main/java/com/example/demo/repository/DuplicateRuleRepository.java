package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DuplicateRule;

@Repository
public interface DuplicateRuleRepository extends JpaRepository<DuplicateRule, Long> {

    // Optional: Find by rule name if needed in tests
    Optional<DuplicateRule> findByRuleName(String ruleName);
}
