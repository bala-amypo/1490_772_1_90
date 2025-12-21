package com.example.demo.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.TicketCategory;
import com.example.demo.repository.TicketCategoryRepository;

@Service
public class TicketCategoryServiceImpl implements TicketCategoryService {

    private final TicketCategoryRepository repo;

    public TicketCategoryServiceImpl(TicketCategoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public TicketCategory createCategory(TicketCategory category) {
        if (repo.existsByCategoryName(category.getCategoryName()))
            throw new IllegalArgumentException("category already exists");
        return repo.save(category);
    }

    @Override
    public List<TicketCategory> getAllCategories() {
        return repo.findAll();
    }

    @Override
    public TicketCategory getCategory(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category not found"));
    }
}
