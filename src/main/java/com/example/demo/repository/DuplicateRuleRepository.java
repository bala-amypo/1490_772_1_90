package com.example.demo.repository;

import com.example.demo.model.DuplicateRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DuplicateRuleRepository extends JpaRepository<DuplicateRule, Long> {
    Optional<DuplicateRule> findByRuleName(String ruleName);
}