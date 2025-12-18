package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.DuplicateDetectionLogModel;
import com.example.demo.service.DuplicateDetectionLogService;

@RestController
@RequestMapping("/api/detection")
public class DuplicateDetectionLogController {

    private final DuplicateDetectionLogService logService;

    public DuplicateDetectionLogController(
            DuplicateDetectionLogService logService) {
        this.logService = logService;
    }

    // GET LOGS FOR A TICKET
    @GetMapping("/ticket/{ticketId}")
    public List<DuplicateDetectionLogModel> getLogsForTicket(
            @PathVariable Long ticketId) {
        return logService.getLogsForTicket(ticketId);
    }

    // GET SINGLE LOG
    @GetMapping("/{id}")
    public DuplicateDetectionLogModel getLog(@PathVariable Long id) {
        return logService.getLog(id);
    }
}
