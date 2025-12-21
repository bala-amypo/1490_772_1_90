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
        // For CRUD only, return empty list or existing logs
        // In a real implementation, this would contain duplicate detection logic
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
    
    // Additional CRUD methods
    public DuplicateDetectionLog createLog(DuplicateDetectionLog log) {
        return detectionLogRepository.save(log);
    }
    
    public List<DuplicateDetectionLog> getAllLogs() {
        return detectionLogRepository.findAll();
    }
    
    public DuplicateDetectionLog updateLog(Long id, DuplicateDetectionLog log) {
        if (detectionLogRepository.existsById(id)) {
            log.setId(id);
            return detectionLogRepository.save(log);
        }
        return null;
    }
    
    public boolean deleteLog(Long id) {
        if (detectionLogRepository.existsById(id)) {
            detectionLogRepository.deleteById(id);
            return true;
        }
        return false;
    }
}