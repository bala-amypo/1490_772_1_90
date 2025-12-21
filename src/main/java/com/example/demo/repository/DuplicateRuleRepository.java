package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.DuplicateRule;

public interface DuplicateRuleRepository extends JpaRepository<DuplicateRule, Long> {
    DuplicateRule findByRuleName(String name);
}
