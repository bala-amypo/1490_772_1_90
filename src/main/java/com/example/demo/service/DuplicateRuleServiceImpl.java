package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.model.DuplicateRule;
import com.example.demo.repository.DuplicateRuleRepository;

@Service
public class DuplicateRuleServiceImpl implements DuplicateRuleService {

    private final DuplicateRuleRepository repo;

    public DuplicateRuleServiceImpl(DuplicateRuleRepository repo) { this.repo = repo; }

    @Override
    public DuplicateRule createRule(DuplicateRule rule) {
        if (repo.findByRuleName(rule.getRuleName()) != null)
            return null;
        return repo.save(rule);
    }

    @Override
    public List<DuplicateRule> getAllRules() {
        return repo.findAll();
    }

    @Override
    public DuplicateRule getRule(Long id) {
        return repo.findById(id).orElse(null);
    }
}
