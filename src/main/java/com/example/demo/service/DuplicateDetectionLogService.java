package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.DuplicateDetectionLogModel;

public interface DuplicateDetectionLogService {

    // RUN DUPLICATE DETECTION
    List<DuplicateDetectionLogModel> runDetection(Long ticketId);

    // GET LOGS FOR A TICKET
    List<DuplicateDetectionLogModel> getLogsForTicket(Long ticketId);

    // GET SINGLE LOG
    DuplicateDetectionLogModel getLog(Long id);
}
