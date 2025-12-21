package com.example.demo.repository;

import com.example.demo.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Existing methods with double underscore
    List<Ticket> findByCategory__ld(Long id);
    List<Ticket> findByUser__ld(Long id);
    List<Ticket> findByStatus(String status);
    List<Ticket> findBySubjectContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
        String subject, String description);
    
    // ADD THESE for test (single underscore)
    List<Ticket> findByCategory_Id(Long id);
    List<Ticket> findByUser_Id(Long id);
    
    // Also add this if test needs it
    List<Ticket> findBySubjectContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
        String searchTerm1, String searchTerm2);
}