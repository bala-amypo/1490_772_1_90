package com.example.demo.controller;

import com.example.demo.model.TicketCategory;
import com.example.demo.service.TicketCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class TicketCategoryController {
    
    private final TicketCategoryService categoryService;
    
    public TicketCategoryController(TicketCategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @PostMapping
    public TicketCategory createCategory(@RequestBody TicketCategory category) {
        return categoryService.createCategory(category);
    }
    
    @GetMapping
    public List<TicketCategory> getAllCategories() {
        return categoryService.getAllCategories();
    }
    
    @GetMapping("/{id}")
    public TicketCategory getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }
}