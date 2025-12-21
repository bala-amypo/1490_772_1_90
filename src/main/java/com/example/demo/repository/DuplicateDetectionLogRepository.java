package com.example.demo.repository;

import com.example.demo.model.DuplicateDetectionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DuplicateDetectionLogRepository extends JpaRepository<DuplicateDetectionLog, Long> {
    
    @Query("SELECT l FROM DuplicateDetectionLog l WHERE l.ticket1.id = :ticketId OR l.ticket2.id = :ticketId")
    List<DuplicateDetectionLog> findByTicket__ld(@Param("ticketId") Long ticketId);
    
    // ADD THIS for test
    List<DuplicateDetectionLog> findByTicket_Id(Long id);
}