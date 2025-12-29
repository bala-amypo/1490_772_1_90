package com.example.demo.controller;

import com.example.demo.model.DuplicateRule;
import com.example.demo.service.DuplicateRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Duplicate Rules", description = "Duplicate detection rule management endpoints")
public class DuplicateRuleController {
    
    private final DuplicateRuleService ruleService;
    
    public DuplicateRuleController(DuplicateRuleService ruleService) {
        this.ruleService = ruleService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new duplicate detection rule")
    public ResponseEntity<DuplicateRule> createRule(@RequestBody DuplicateRule rule) {
        return ResponseEntity.ok(ruleService.createRule(rule));
    }
    
    @GetMapping
    @Operation(summary = "Get all rules")
    public ResponseEntity<List<DuplicateRule>> getAllRules() {
        return ResponseEntity.ok(ruleService.getAllRules());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get rule by ID")
    public ResponseEntity<DuplicateRule> getRule(@PathVariable Long id) {
        return ResponseEntity.ok(ruleService.getRule(id));
    }
}