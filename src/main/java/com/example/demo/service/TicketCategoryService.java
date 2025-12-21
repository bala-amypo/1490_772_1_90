package com.example.demo.service;

import java.util.List;
import com.example.demo.model.TicketCategory;

public interface TicketCategoryService {
    TicketCategory createCategory(TicketCategory category);
    List<TicketCategory> getAllCategories();
    TicketCategory getCategory(Long id);
}
