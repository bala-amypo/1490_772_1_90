package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DuplicateRule;
import com.example.demo.repository.DuplicateRuleRepository;

@Service
public class DuplicateRuleServiceImpl implements DuplicateRuleService {

    private final DuplicateRuleRepository repo;

    public DuplicateRuleServiceImpl(DuplicateRuleRepository repo) { this.repo = repo; }

    @Override
    public DuplicateRule createRule(DuplicateRule rule) {
        if (repo.findByRuleName(rule.getRuleName()) != null)
            throw new IllegalArgumentException("rule already exists");
        return repo.save(rule);
    }

    @Override
    public List<DuplicateRule> getAllRules() {
        return repo.findAll();
    }

    @Override
    public DuplicateRule getRule(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("rule not found"));
    }
}
