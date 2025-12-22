package com.example.demo.service;

import java.util.List;
import com.example.demo.model.DuplicateRule;

public interface DuplicateRuleService {
    DuplicateRule save(DuplicateRule rule);
    List<DuplicateRule> getAll();
}
