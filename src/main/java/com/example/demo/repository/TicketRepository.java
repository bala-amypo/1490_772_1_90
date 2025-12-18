package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.TicketModel;

public interface TicketRepository extends JpaRepository<TicketModel, Long> {

    List<TicketModel> findByCategory_Id(Long id);

    List<TicketModel> findByUser_Id(Long id);

    List<TicketModel> findByStatus(String status);

    List<TicketModel>
        findBySubjectContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String subject,
            String description
        );
}
