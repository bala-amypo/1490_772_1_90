package com.example.demo.controller;

import com.example.demo.model.TicketCategory;
import com.example.demo.service.TicketCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Categories", description = "Ticket category management endpoints")
public class TicketCategoryController {
    
    private final TicketCategoryService categoryService;
    
    public TicketCategoryController(TicketCategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new category")
    public ResponseEntity<TicketCategory> createCategory(@RequestBody TicketCategory category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }
    
    @GetMapping
    @Operation(summary = "Get all categories")
    public ResponseEntity<List<TicketCategory>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<TicketCategory> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }
}