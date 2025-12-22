package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.model.DuplicateRule;
import com.example.demo.service.DuplicateRuleService;

@RestController
@RequestMapping("/api/rules")
public class DuplicateRuleController {

    private final DuplicateRuleService service;

    public DuplicateRuleController(DuplicateRuleService service) {
        this.service = service;
    }

    @PostMapping
    public DuplicateRule save(@RequestBody DuplicateRule rule) {
        return service.save(rule);
    }

    @GetMapping
    public List<DuplicateRule> getAll() {
        return service.getAll();
    }
}