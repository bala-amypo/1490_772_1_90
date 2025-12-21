package com.example.demo.repository;

import com.example.demo.model.DuplicateDetectionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DuplicateDetectionLogRepository extends JpaRepository<DuplicateDetectionLog, Long> {
    // Find duplicate detection logs by ticket ID
    // This assumes your DuplicateDetectionLog entity has a property named "ticket" 
    // with an "id" field (ticket.id)
    List<DuplicateDetectionLog> findByTicket_Id(Long id);
}