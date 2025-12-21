package com.example.demo.repository;

import com.example.demo.model.DuplicateRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DuplicateRuleRepository extends JpaRepository<DuplicateRule, Long> {
    // This returns DuplicateRule (could be null)
    DuplicateRule findByRuleName(String name);
}