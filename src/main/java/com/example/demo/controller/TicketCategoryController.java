package com.example.demo.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.TicketCategory;
import com.example.demo.service.TicketCategoryService;

@RestController
@RequestMapping("/api/categories")
public class TicketCategoryController {

    private final TicketCategoryService service;

    public TicketCategoryController(TicketCategoryService service) {
        this.service = service;
    }

    @PostMapping
    public TicketCategory create(@RequestBody TicketCategory category) {
        return service.createCategory(category);
    }

    @GetMapping
    public List<TicketCategory> getAll() {
        return service.getAllCategories();
    }

    @GetMapping("/{id}")
    public TicketCategory get(@PathVariable Long id) {
        return service.getCategory(id);
    }
}
