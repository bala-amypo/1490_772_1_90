package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Find tickets by status
    List<Ticket> findByStatus(String status);

    // Find tickets by user ID
    List<Ticket> findByUser_Id(Long userId);
    
    // Add other methods you need here
}
