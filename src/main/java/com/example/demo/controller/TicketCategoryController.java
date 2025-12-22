package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
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
    public TicketCategory save(@RequestBody TicketCategory c) {
        return service.save(c);
    }

    @GetMapping
    public List<TicketCategory> getAll() {
        return service.getAll();
    }
}