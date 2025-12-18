package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.TicketCategoryModel;
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
            throw new RuntimeException("Category name cannot be empty");
        }

        if (category.getDescription() == null || category.getDescription().trim().isEmpty()) {
            throw new RuntimeException("Description cannot be empty");
        }

        if (categoryRepo.existsByCategoryName(category.getCategoryName())) {
            throw new RuntimeException("Category already exists");
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
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
