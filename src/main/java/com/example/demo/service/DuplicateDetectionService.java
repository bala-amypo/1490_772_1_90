package com.example.demo.service;

import java.util.List;
import com.example.demo.model.DuplicateDetectionLog;

public interface DuplicateDetectionService {
    List<DuplicateDetectionLog> detectDuplicates(Long ticketId);
    List<DuplicateDetectionLog> getLogsForTicket(Long ticketId);
    DuplicateDetectionLog getLog(Long id);
}