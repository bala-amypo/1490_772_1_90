package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.DuplicateDetectionLogModel;

public interface DuplicateDetectionLogRepository
        extends JpaRepository<DuplicateDetectionLogModel, Long> {

    List<DuplicateDetectionLogModel> findByTicket_Id(Long ticketId);
}
