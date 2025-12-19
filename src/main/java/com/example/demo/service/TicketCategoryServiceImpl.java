package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.DuplicateRuleModel;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DuplicateRuleRepository;

@Service
public class DuplicateRuleServiceImpl implements DuplicateRuleService {

    private final DuplicateRuleRepository ruleRepository;

    public DuplicateRuleServiceImpl(DuplicateRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public DuplicateRuleModel createRule(DuplicateRuleModel rule) {

        validateRule(rule);
        return ruleRepository.save(rule);
    }

    @Override
    public List<DuplicateRuleModel> getAllRules() {
        return ruleRepository.findAll();
    }

    @Override
    public DuplicateRuleModel getRule(Long id) {
        return ruleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "DuplicateRule not found with id: " + id));
    }

    @Override
    public DuplicateRuleModel updateRule(Long id, DuplicateRuleModel rule) {

        DuplicateRuleModel existing = getRule(id);

        validateRule(rule);

        existing.setFieldName(rule.getFieldName());
        existing.setThreshold(rule.getThreshold());

        return ruleRepository.save(existing);
    }

    @Override
    public void deleteRule(Long id) {
        DuplicateRuleModel rule = getRule(id);
        ruleRepository.delete(rule);
    }

    // 🔹 COMMON VALIDATION METHOD
    private void validateRule(DuplicateRuleModel rule) {

        if (rule == null) {
            throw new IllegalArgumentException("Rule data cannot be null");
        }

        if (rule.getFieldName() == null || rule.getFieldName().trim().isEmpty()) {
            throw new IllegalArgumentException("Field name cannot be empty");
        }

        if (rule.getThreshold() < 0 || rule.getThreshold() > 1) {
            throw new IllegalArgumentException("Threshold must be between 0 and 1");
        }
    }
}
