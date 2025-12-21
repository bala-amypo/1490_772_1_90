package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DuplicateDetectionLog;
import com.example.demo.model.DuplicateRule;
import com.example.demo.model.Ticket;
import com.example.demo.repository.DuplicateDetectionLogRepository;
import com.example.demo.repository.DuplicateRuleRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.util.TextSimilarityUtil;

@Service
public class DuplicateDetectionServiceImpl implements DuplicateDetectionService {

    private final TicketRepository ticketRepo;
    private final DuplicateRuleRepository ruleRepo;
    private final DuplicateDetectionLogRepository logRepo;

    public DuplicateDetectionServiceImpl(TicketRepository ticketRepo,
                                         DuplicateRuleRepository ruleRepo,
                                         DuplicateDetectionLogRepository logRepo) {
        this.ticketRepo = ticketRepo;
        this.ruleRepo = ruleRepo;
        this.logRepo = logRepo;
    }

    @Override
    public List<DuplicateDetectionLog> detectDuplicates(Long ticketId) {
        // Fetch the target ticket
        Ticket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        // Fetch open tickets (excluding the current ticket)
        List<Ticket> openTickets = ticketRepo.findByStatus("OPEN");
        
        // Fetch active rules
        List<DuplicateRule> activeRules = ruleRepo.findAll().stream()
                .filter(rule -> rule.getIsActive() != null && rule.getIsActive())
                .toList();

        List<DuplicateDetectionLog> logs = new ArrayList<>();

        // Compare with each open ticket
        for (Ticket otherTicket : openTickets) {
            // Skip comparing with itself
            if (otherTicket.getId().equals(ticket.getId())) {
                continue;
            }

            // Apply each active rule
            for (DuplicateRule rule : activeRules) {
                double similarityScore = calculateSimilarityScore(ticket, otherTicket, rule);

                // Check if score meets or exceeds the threshold
                if (similarityScore >= rule.getSimilarityThreshold()) {
                    // Create duplicate detection log
                    DuplicateDetectionLog log = new DuplicateDetectionLog();
                    log.setTicket1(ticket);
                    log.setTicket2(otherTicket);
                    log.setSimilarityScore(similarityScore);
                    log.setDetectionTime(LocalDateTime.now());
                    
                    // Save to database
                    logRepo.save(log);
                    logs.add(log);
                }
            }
        }

        return logs;
    }

    private double calculateSimilarityScore(Ticket ticket1, Ticket ticket2, DuplicateRule rule) {
        String matchType = rule.getMatchType();
        
        if (matchType == null) {
            return 0.0;
        }
        
        switch (matchType.toUpperCase()) {
            case "EXACT_MATCH":
                // Exact match on description
                return ticket1.getDescription().equalsIgnoreCase(ticket2.getDescription()) ? 1.0 : 0.0;
                
            case "KEYWORD":
                // Check if subject contains common keywords or exact match
                String subject1 = ticket1.getSubject().toLowerCase();
                String subject2 = ticket2.getSubject().toLowerCase();
                
                // Simple keyword matching - check if subjects are exactly the same
                if (subject1.equals(subject2)) {
                    return 1.0;
                }
                return 0.0;
                
            case "SIMILARITY":
                // Use TextSimilarityUtil.similarity() as required
                return TextSimilarityUtil.similarity(
                    ticket1.getDescription(), 
                    ticket2.getDescription()
                );
                
            default:
                return 0.0;
        }
    }

    @Override
    public List<DuplicateDetectionLog> getLogsForTicket(Long ticketId) {
        // Check if ticket exists first
        if (!ticketRepo.existsById(ticketId)) {
            throw new ResourceNotFoundException("Ticket not found");
        }
        
        // Use the repository method
        return logRepo.findByTicket__ld(ticketId);
    }

    @Override
    public DuplicateDetectionLog getLog(Long id) {
        return logRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Duplicate detection log not found"));
    }
}