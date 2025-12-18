package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.DuplicateDetectionLogModel;
import com.example.demo.repository.DuplicateDetectionLogRepository;

@Service
public class DuplicateDetectionLogServiceImpl
        implements DuplicateDetectionLogService {

    private final DuplicateDetectionLogRepository logRepository;

    public DuplicateDetectionLogServiceImpl(
            DuplicateDetectionLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public List<DuplicateDetectionLogModel> getLogsForTicket(Long ticketId) {
        return logRepository.findByTicket_Id(ticketId);
    }

    @Override
    public DuplicateDetectionLogModel getLog(Long id) {
        return logRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Log not found"));
    }
}
