package com.example.demo.controller;

import com.example.demo.model.DuplicateDetectionLog;
import com.example.demo.service.DuplicateDetectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/duplicate-detection")
@Tag(name = "Duplicate Detection", description = "APIs for detecting duplicate tickets")
public class DuplicateDetectionController {

    @Autowired
    private DuplicateDetectionService duplicateDetectionService;

    @PostMapping("/detect/{ticketId}")
    @Operation(summary = "Detect duplicates for a ticket")
    public ResponseEntity<List<DuplicateDetectionLog>> detectDuplicates(@PathVariable Long ticketId) {
        List<DuplicateDetectionLog> logs = duplicateDetectionService.detectDuplicates(ticketId);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/logs/ticket/{ticketId}")
    @Operation(summary = "Get duplicate detection logs for a ticket")
    public ResponseEntity<List<DuplicateDetectionLog>> getLogsForTicket(@PathVariable Long ticketId) {
        List<DuplicateDetectionLog> logs = duplicateDetectionService.getLogsForTicket(ticketId);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/logs/{id}")
    @Operation(summary = "Get duplicate detection log by ID")
    public ResponseEntity<DuplicateDetectionLog> getLog(@PathVariable Long id) {
        DuplicateDetectionLog log = duplicateDetectionService.getLog(id);
        return ResponseEntity.ok(log);
    }
}