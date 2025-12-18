package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.DuplicateRuleModel;
import com.example.demo.service.DuplicateRuleService;

@RestController
@RequestMapping("/api/rules")
public class DuplicateRuleController {

    private final DuplicateRuleService ruleService;

    public DuplicateRuleController(DuplicateRuleService ruleService) {
        this.ruleService = ruleService;
    }

    // CREATE RULE
    @PostMapping
    public DuplicateRuleModel createRule(@RequestBody DuplicateRuleModel rule) {
        return ruleService.createRule(rule);
    }

    // GET ALL RULES
    @GetMapping
    public List<DuplicateRuleModel> getAllRules() {
        return ruleService.getAllRules();
    }

    // GET RULE BY ID
    @GetMapping("/{id}")
    public DuplicateRuleModel getRule(@PathVariable Long id) {
        return ruleService.getRule(id);
    }

    // UPDATE RULE
    @PutMapping("/{id}")
    public DuplicateRuleModel updateRule(
            @PathVariable Long id,
            @RequestBody DuplicateRuleModel rule) {
        return ruleService.updateRule(id, rule);
    }

    // DELETE RULE
    @DeleteMapping("/{id}")
    public void deleteRule(@PathVariable Long id) {
        ruleService.deleteRule(id);
    }
}
