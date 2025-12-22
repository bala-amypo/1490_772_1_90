package com.example.demo.service;

import java.util.List;
import com.example.demo.model.TicketCategory;

public interface TicketCategoryService {
    TicketCategory save(TicketCategory category);
    List<TicketCategory> getAll();
}
