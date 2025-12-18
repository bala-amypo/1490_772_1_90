package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.DuplicateDetectionLogModel;

public interface DuplicateDetectionLogService {

    List<DuplicateDetectionLogModel> getLogsForTicket(Long ticketId);

    DuplicateDetectionLogModel getLog(Long id);
}
