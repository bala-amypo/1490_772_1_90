package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.model.DuplicateRule;
import com.example.demo.repository.DuplicateRuleRepository;
import com.example.demo.service.DuplicateRuleService;

@Service
public class DuplicateRuleServiceImpl implements DuplicateRuleService {

    private final DuplicateRuleRepository ruleRepository;
    public DuplicateRuleServiceImpl(DuplicateRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public DuplicateRule createRule(DuplicateRule rule) {
        return ruleRepository.save(rule);
    }

    @Override
    public DuplicateRule getRule(long id) {
        return ruleRepository.findById(id).orElse(null);
    }
}
