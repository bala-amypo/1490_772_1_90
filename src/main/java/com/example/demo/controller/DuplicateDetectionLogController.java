package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.model.DuplicateDetectionLog;
import com.example.demo.service.DuplicateDetectionLogService;

@RestController
@RequestMapping("/api/detection")
public class DuplicateDetectionLogController {

    private final DuplicateDetectionLogService service;

    public DuplicateDetectionLogController(DuplicateDetectionLogService service) {
        this.service = service;
    }

    @GetMapping("/ticket/{ticketId}")
    public List<DuplicateDetectionLog> getByTicket(@PathVariable Long ticketId) {
        return service.getByTicketId(ticketId);
    }
}
