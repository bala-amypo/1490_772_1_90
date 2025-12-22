package com.example.demo.service;

import java.util.List;

import com.example.demo.model.DuplicateRule;

public interface DuplicateRuleService {

    DuplicateRule createRule(DuplicateRule rule);

    DuplicateRule getRule(long id);

    List<DuplicateRule> getAll();   
}
