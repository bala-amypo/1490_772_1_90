package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.TicketCategoryModel;

public interface TicketCategoryService {

    TicketCategoryModel createCategory(TicketCategoryModel category);

    List<TicketCategoryModel> getAllCategories();

    TicketCategoryModel getCategoryById(Long id);
}
