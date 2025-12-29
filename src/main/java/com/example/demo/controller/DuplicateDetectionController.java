package com.example.demo.controller;

import com.example.demo.model.DuplicateDetectionLog;
import com.example.demo.service.DuplicateDetectionService;
import com.example.demo.service.impl.DuplicateDetectionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detection")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Duplicate Detection", description = "Duplicate detection endpoints")
public class DuplicateDetectionController {
    
    private final DuplicateDetectionService detectionService;
    
    public DuplicateDetectionController(DuplicateDetectionService detectionService) {
        this.detectionService = detectionService;
    }
    
    @GetMapping("/run/{ticketId}")
    @Operation(summary = "Run duplicate detection for a ticket")
    public ResponseEntity<List<DuplicateDetectionLog>> runDuplicateDetection(@PathVariable Long ticketId) {
        return ResponseEntity.ok(detectionService.detectDuplicates(ticketId));
    }
    
    @GetMapping("/ticket/{ticketId}")
    @Operation(summary = "Get all detection logs for a ticket")
    public ResponseEntity<List<DuplicateDetectionLog>> getLogsForTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(detectionService.getLogsForTicket(ticketId));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get detection log by ID")
    public ResponseEntity<DuplicateDetectionLog> getLog(@PathVariable Long id) {
        return ResponseEntity.ok(((DuplicateDetectionServiceImpl) detectionService).getLog(id));
    }
}