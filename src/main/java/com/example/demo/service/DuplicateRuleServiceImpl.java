package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.model.DuplicateRule;
import com.example.demo.repository.DuplicateRuleRepository;
import com.example.demo.service.DuplicateRuleService;

@Service
public class DuplicateRuleServiceImpl implements DuplicateRuleService {

    private final DuplicateRuleRepository repo;

    public DuplicateRuleServiceImpl(DuplicateRuleRepository repo) {
        this.repo = repo;
    }

    public DuplicateRule save(DuplicateRule rule) {
        return repo.save(rule);
    }

    public List<DuplicateRule> getAll() {
        return repo.findAll();
    }
}
