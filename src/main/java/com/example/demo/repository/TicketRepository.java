package com.example.demo.repository;

import com.example.demo.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatus(String status);
    List<Ticket> findByCategory_Id(Long categoryId);
    List<Ticket> findByUser_Id(Long userId);
    List<Ticket> findBySubjectContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String subject, String description);
}