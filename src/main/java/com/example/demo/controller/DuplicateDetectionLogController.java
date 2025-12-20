package com.example.demo.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.DuplicateDetectionLog;
import com.example.demo.service.DuplicateDetectionLogService;

@RestController
@RequestMapping("/api/detection")
public class DuplicateDetectionLogController {

    private final DuplicateDetectionLogService service;

    public DuplicateDetectionLogController(DuplicateDetectionLogService service) { this.service = service; }

    @GetMapping("/run/{ticketId}")
    public List<DuplicateDetectionLog> detect(@PathVariable Long ticketId) {
        return service.detectDuplicates(ticketId);
    }

    @GetMapping("/ticket/{ticketId}")
    public List<DuplicateDetectionLog> getLogs(@PathVariable Long ticketId) {
        return service.getLogsForTicket(ticketId);
    }

    @GetMapping("/{id}")
    public DuplicateDetectionLog get(@PathVariable Long id) {
        return service.getLog(id);
    }
}
