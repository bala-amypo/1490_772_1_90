package com.example.demo.service.impl;

import com.example.demo.model.DuplicateDetectionLog;
import com.example.demo.repository.DuplicateDetectionLogRepository;
import com.example.demo.service.DuplicateDetectionService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DuplicateDetectionServiceImpl implements DuplicateDetectionService {
    private final DuplicateDetectionLogRepository detectionLogRepository;
    
    public DuplicateDetectionServiceImpl(DuplicateDetectionLogRepository detectionLogRepository) {
        this.detectionLogRepository = detectionLogRepository;
    }
    
    @Override
    public List<DuplicateDetectionLog> detectDuplicates(Long ticketId) {
        // Return empty list for demo - in real app, this would run duplicate detection logic
        return List.of();
    }
    
    @Override
    public List<DuplicateDetectionLog> getLogsForTicket(Long ticketId) {
        return detectionLogRepository.findByTicketId(ticketId);
    }
    
    @Override
    public DuplicateDetectionLog getLog(Long id) {
        return detectionLogRepository.findById(id).orElse(null);
    }
}