package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.DuplicateRuleModel;

public interface DuplicateRuleService {

    DuplicateRuleModel createRule(DuplicateRuleModel rule);

    List<DuplicateRuleModel> getAllRules();

    DuplicateRuleModel getRule(Long id);

    DuplicateRuleModel updateRule(Long id, DuplicateRuleModel rule);

    void deleteRule(Long id);
}
