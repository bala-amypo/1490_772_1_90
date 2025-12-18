package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.TicketCategoryModel;
import com.example.demo.service.TicketCategoryService;

@RestController
@RequestMapping("/api/categories")
public class TicketCategoryController {

    private final TicketCategoryService categoryService;

    public TicketCategoryController(TicketCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // CREATE CATEGORY
    @PostMapping
    public TicketCategoryModel createCategory(@RequestBody TicketCategoryModel category) {
        return categoryService.createCategory(category);
    }

    // GET ALL CATEGORIES
    @GetMapping
    public List<TicketCategoryModel> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // GET CATEGORY BY ID
    @GetMapping("/{id}")
    public TicketCategoryModel getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }
}
