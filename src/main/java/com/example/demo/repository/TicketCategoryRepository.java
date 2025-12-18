package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.TicketCategoryModel;

public interface TicketCategoryRepository
        extends JpaRepository<TicketCategoryModel, Long> {

    boolean existsByCategoryName(String categoryName);
}
