package com.example.demo.repository;

import com.example.demo.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Find tickets by category ID
    List<Ticket> findByCategory_Id(Long categoryId);

    // Find tickets by user ID
    List<Ticket> findByUser_Id(Long userId);

    // Find tickets by status
    List<Ticket> findByStatus(String status);

    // Search tickets by subject or description
    List<Ticket> findBySubjectContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String subject, String description);
}


















// package com.example.demo.repository;

// import com.example.demo.model.Ticket;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import java.util.List;

// @Repository
// public interface TicketRepository extends JpaRepository<Ticket, Long> {
//     List<Ticket> findByCategoryId(Long id);
//     List<Ticket> findByUserId(Long id);
//     List<Ticket> findByStatus(String status);
//     List<Ticket> findBySubjectContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String subject, String description);
// }