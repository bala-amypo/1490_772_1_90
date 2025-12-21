package com.example.demo.repository;

import com.example.demo.model.DuplicateRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DuplicateRuleRepository extends JpaRepository<DuplicateRule, Long> {
    // CHANGE THIS: Return Optional<DuplicateRule> instead of DuplicateRule
    Optional<DuplicateRule> findByRuleName(String name);
}