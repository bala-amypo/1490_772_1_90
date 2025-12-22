package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.model.DuplicateDetectionLog;
import com.example.demo.repository.DuplicateDetectionLogRepository;
import com.example.demo.service.DuplicateDetectionLogService;

@Service
public class DuplicateDetectionLogServiceImpl implements DuplicateDetectionLogService {

    private final DuplicateDetectionLogRepository repo;

    public DuplicateDetectionLogServiceImpl(DuplicateDetectionLogRepository repo) {
        this.repo = repo;
    }

    public List<DuplicateDetectionLog> getByTicketId(Long ticketId) {
        return repo.findByTicketId(ticketId);
    }
}