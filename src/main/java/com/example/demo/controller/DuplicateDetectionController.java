package com.example.demo.controller;

import com.example.demo.model.DuplicateDetectionLog;
import com.example.demo.service.DuplicateDetectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/detection")
public class DuplicateDetectionController {
    private final DuplicateDetectionService detectionService;

    public DuplicateDetectionController(DuplicateDetectionService detectionService) {
        this.detectionService = detectionService;
    }

    @PostMapping("/detect/{ticketId}")
    public ResponseEntity<List<DuplicateDetectionLog>> detectDuplicates(@PathVariable Long ticketId) {
        return ResponseEntity.ok(detectionService.detectDuplicates(ticketId));
    }

    @GetMapping("/logs/{ticketId}")
    public ResponseEntity<List<DuplicateDetectionLog>> getLogsForTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(detectionService.getLogsForTicket(ticketId));
    }
}