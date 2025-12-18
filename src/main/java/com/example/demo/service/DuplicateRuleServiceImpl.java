package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.DuplicateRuleModel;
import com.example.demo.repository.DuplicateRuleRepository;

@Service
public class DuplicateRuleServiceImpl implements DuplicateRuleService {

    private final DuplicateRuleRepository ruleRepository;

    public DuplicateRuleServiceImpl(DuplicateRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public DuplicateRuleModel createRule(DuplicateRuleModel rule) {
        return ruleRepository.save(rule);
    }

    @Override
    public List<DuplicateRuleModel> getAllRules() {
        return ruleRepository.findAll();
    }

    @Override
    public DuplicateRuleModel getRule(Long id) {
        return ruleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
    }

    @Override
    public DuplicateRuleModel updateRule(Long id, DuplicateRuleModel rule) {
        DuplicateRuleModel existing = getRule(id);
        existing.setFieldName(rule.getFieldName());
        existing.setThreshold(rule.getThreshold());
        return ruleRepository.save(existing);
    }

    @Override
    public void deleteRule(Long id) {
        DuplicateRuleModel rule = getRule(id);
        ruleRepository.delete(rule);
    }
}
