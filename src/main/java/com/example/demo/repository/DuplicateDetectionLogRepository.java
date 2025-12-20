package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DuplicateDetectionLog;

@Repository
public interface DuplicateDetectionLogRepository extends JpaRepository<DuplicateDetectionLog, Long> {
    
    // Optional: find by ticket IDs or other filters if needed
}
