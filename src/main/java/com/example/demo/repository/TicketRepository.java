package com.example.demo.repository;

import com.example.demo.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    // Find tickets by category ID (using double underscore as specified)
    // Note: In Spring Data JPA, __ld is unusual. This might be a typo for _id
    // But following your exact requirement:
    List<Ticket> findByCategory__ld(Long id);
    
    // Find tickets by user ID (using double underscore as specified)
    List<Ticket> findByUser__ld(Long id);
    
    // Find tickets by status
    List<Ticket> findByStatus(String status);
    
    // Find tickets by subject or description containing text (case-insensitive)
    List<Ticket> findBySubjectContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
        String subject, String description);
    }
}