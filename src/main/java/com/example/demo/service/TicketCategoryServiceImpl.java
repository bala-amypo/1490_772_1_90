package com.example.demo.service.impl;

import com.example.demo.model.TicketCategory;
import com.example.demo.repository.TicketCategoryRepository;
import com.example.demo.service.TicketCategoryService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TicketCategoryServiceImpl implements TicketCategoryService {
    private final TicketCategoryRepository categoryRepository;
    
    public TicketCategoryServiceImpl(TicketCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    @Override
    public TicketCategory createCategory(TicketCategory category) {
        return categoryRepository.save(category);
    }
    
    @Override
    public List<TicketCategory> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    @Override
    public TicketCategory getCategory(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}