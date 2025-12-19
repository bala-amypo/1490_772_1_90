package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.TicketCategoryModel;
import com.example.demo.repository.TicketCategoryRepository;

@Service
public class TicketCategoryServiceImpl implements TicketCategoryService {

    private final TicketCategoryRepository categoryRepo;

    public TicketCategoryServiceImpl(TicketCategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public TicketCategoryModel createCategory(TicketCategoryModel category) {

        if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }

        if (category.getDescription() == null || category.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        if (categoryRepo.existsByCategoryName(category.getCategoryName())) {
            throw new IllegalArgumentException("Category already exists");
        }

        return categoryRepo.save(category);
    }

    @Override
    public List<TicketCategoryModel> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public TicketCategoryModel getCategoryById(Long id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }
}
