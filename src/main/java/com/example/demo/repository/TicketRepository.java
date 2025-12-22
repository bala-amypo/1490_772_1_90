package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.demo.model.Ticket;
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByStatus(String status);

    List<Ticket> findByUser_Id(long userId);

    List<Ticket> findByCategory_Id(long categoryId);

    List<Ticket> findBySubjectContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String subject, String description);
}
