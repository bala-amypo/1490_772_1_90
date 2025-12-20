package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.entity.DuplicateDetectionLogModel;
import com.example.demo.entity.DuplicateRuleModel;
import com.example.demo.entity.TicketModel;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DuplicateDetectionLogRepository;
import com.example.demo.repository.DuplicateRuleRepository;
import com.example.demo.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Transactional
public class DuplicateDetectionLogServiceImpl implements DuplicateDetectionLogService {

    private static final Logger logger = LoggerFactory.getLogger(DuplicateDetectionLogServiceImpl.class);
    
    private final DuplicateDetectionLogRepository logRepository;
    private final TicketRepository ticketRepository;
    private final DuplicateRuleRepository ruleRepository;
    private final SimilarityService similarityService;

    public DuplicateDetectionLogServiceImpl(
            DuplicateDetectionLogRepository logRepository,
            TicketRepository ticketRepository,
            DuplicateRuleRepository ruleRepository,
            SimilarityService similarityService) {
        this.logRepository = logRepository;
        this.ticketRepository = ticketRepository;
        this.ruleRepository = ruleRepository;
        this.similarityService = similarityService;
    }

    @Override
    @Transactional
    public List<DuplicateDetectionLogModel> runDetection(Long ticketId) {
        logger.info("Running duplicate detection for ticket ID: {}", ticketId);
        
        validateTicketId(ticketId);
        
        // Clear previous detection logs for this ticket
        deleteLogsByTicketId(ticketId);
        
        // Get the target ticket
        TicketModel targetTicket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Ticket not found with id: " + ticketId));
        
        // Get all active duplicate rules
        List<DuplicateRuleModel> rules = ruleRepository.findAll();
        if (rules.isEmpty()) {
            logger.warn("No duplicate rules configured. Skipping detection.");
            return new ArrayList<>();
        }
        
        // Get all tickets except the target one
        List<TicketModel> allTickets = ticketRepository.findAll().stream()
                .filter(ticket -> !ticket.getId().equals(ticketId))
                .collect(Collectors.toList());
        
        if (allTickets.isEmpty()) {
            logger.info("No other tickets found for comparison.");
            return new ArrayList<>();
        }
        
        List<DuplicateDetectionLogModel> detectionResults = new ArrayList<>();
        
        // Compare with each existing ticket
        for (TicketModel existingTicket : allTickets) {
            try {
                List<DuplicateDetectionLogModel> ticketLogs = 
                    compareTickets(targetTicket, existingTicket, rules);
                detectionResults.addAll(ticketLogs);
            } catch (Exception e) {
                logger.error("Error comparing tickets {} and {}: {}", 
                    targetTicket.getId(), existingTicket.getId(), e.getMessage());
            }
        }
        
        // Save all detection logs
        if (!detectionResults.isEmpty()) {
            logRepository.saveAll(detectionResults);
            logger.info("Duplicate detection completed. Found {} potential duplicates.", 
                    detectionResults.size());
        } else {
            logger.info("No duplicates found for ticket ID: {}", ticketId);
        }
        
        return detectionResults;
    }

    @Override
    public List<DuplicateDetectionLogModel> getLogsForTicket(Long ticketId) {
        validateTicketId(ticketId);
        return logRepository.findByTicket_Id(ticketId);
    }

    @Override
    public DuplicateDetectionLogModel getLog(Long id) {
        return logRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "DuplicateDetectionLog not found with id: " + id));
    }

    private List<DuplicateDetectionLogModel> compareTickets(
            TicketModel ticket1, TicketModel ticket2, 
            List<DuplicateRuleModel> rules) {
        
        List<DuplicateDetectionLogModel> logs = new ArrayList<>();
        
        for (DuplicateRuleModel rule : rules) {
            try {
                double similarityScore = calculateSimilarityByRule(ticket1, ticket2, rule);
                
                // If similarity meets or exceeds threshold, log it as potential duplicate
                if (similarityScore >= rule.getThreshold()) {
                    DuplicateDetectionLogModel logEntry = createLogEntry(
                        ticket1, ticket2, rule, similarityScore);
                    logs.add(logEntry);
                }
            } catch (Exception e) {
                logger.warn("Failed to apply rule '{}' to tickets {} and {}: {}", 
                    rule.getFieldName(), ticket1.getId(), ticket2.getId(), e.getMessage());
            }
        }
        
        return logs;
    }

    private double calculateSimilarityByRule(
            TicketModel ticket1, TicketModel ticket2, 
            DuplicateRuleModel rule) {
        
        String fieldName = rule.getFieldName().toLowerCase();
        
        switch (fieldName) {
            case "subject":
                return similarityService.calculateSimilarity(
                    ticket1.getSubject(), ticket2.getSubject());
                
            case "description":
                return similarityService.calculateSimilarity(
                    ticket1.getDescription(), ticket2.getDescription());
                
            case "combined":
                // Combine subject and description for comparison
                double subjectSimilarity = similarityService.calculateSimilarity(
                    ticket1.getSubject(), ticket2.getSubject());
                double descSimilarity = similarityService.calculateSimilarity(
                    ticket1.getDescription(), ticket2.getDescription());
                return (subjectSimilarity + descSimilarity) / 2;
                
            default:
                throw new IllegalArgumentException(
                    "Unsupported field for duplicate detection: " + rule.getFieldName());
        }
    }

    private DuplicateDetectionLogModel createLogEntry(
            TicketModel sourceTicket, TicketModel duplicateTicket,
            DuplicateRuleModel rule, double similarityScore) {
        
        DuplicateDetectionLogModel logEntry = new DuplicateDetectionLogModel();
        logEntry.setTicket(sourceTicket);
        logEntry.setDuplicateTicket(duplicateTicket);
        logEntry.setRule(rule);
        logEntry.setSimilarityScore(similarityScore);
        logEntry.setDetectionTimestamp(LocalDateTime.now());
        logEntry.setStatus("DETECTED");
        logEntry.setMatchField(rule.getFieldName());
        logEntry.setNotes(String.format(
            "Similarity score: %.2f (Threshold: %.2f)", 
            similarityScore, rule.getThreshold()));
        
        return logEntry;
    }

    private void validateTicketId(Long ticketId) {
        if (ticketId == null) {
            throw new IllegalArgumentException("Ticket ID cannot be null");
        }
        if (ticketId <= 0) {
            throw new IllegalArgumentException("Ticket ID must be positive");
        }
    }
    
    private void deleteLogsByTicketId(Long ticketId) {
        List<DuplicateDetectionLogModel> existingLogs = logRepository.findByTicket_Id(ticketId);
        if (!existingLogs.isEmpty()) {
            logRepository.deleteAll(existingLogs);
            logger.debug("Deleted {} previous detection logs for ticket ID: {}", 
                existingLogs.size(), ticketId);
        }
    }
}