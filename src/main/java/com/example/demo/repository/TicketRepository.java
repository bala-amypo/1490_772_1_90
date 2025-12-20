package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Search by subject or description (case-insensitive)
    List<Ticket> findBySubjectContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String subject, String description);
}
