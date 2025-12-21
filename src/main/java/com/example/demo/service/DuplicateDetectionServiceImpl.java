package com.example.demo.service;

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

    // CORRECT CONSTRUCTOR SIGNATURE as per requirements
    public DuplicateDetectionServiceImpl(TicketRepository ticketRepo,
                                         DuplicateRuleRepository ruleRepo,
                                         DuplicateDetectionLogRepository logRepo) {
        this.ticketRepo = ticketRepo;
        this.ruleRepo = ruleRepo;
        this.logRepo = logRepo;
    }

    @Override
    public List<DuplicateDetectionLog> detectDuplicates(Long ticketId) {
        // 1. Fetch the target ticket - use exact error message
        Ticket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        // 2. Fetch open tickets (excluding the current ticket)
        List<Ticket> openTickets = ticketRepo.findByStatus("OPEN");
        
        // 3. Fetch active rules
        List<DuplicateRule> activeRules = ruleRepo.findAll().stream()
                .filter(rule -> rule.getIsActive() != null && rule.getIsActive())
                .toList();

        List<DuplicateDetectionLog> logs = new ArrayList<>();

        // 4. Compare with each open ticket
        for (Ticket otherTicket : openTickets) {
            // Skip comparing with itself
            if (otherTicket.getId().equals(ticket.getId())) {
                continue;
            }

            // 5. Apply each active rule
            for (DuplicateRule rule : activeRules) {
                double similarityScore = calculateSimilarityScore(ticket, otherTicket, rule);

                // 6. Check if score meets or exceeds the threshold
                if (similarityScore >= rule.getSimilarityThreshold()) {
                    // 7. Create duplicate detection log
                    DuplicateDetectionLog log = new DuplicateDetectionLog();
                    log.setTicket1(ticket);
                    log.setTicket2(otherTicket);
                    log.setSimilarityScore(similarityScore);
                    log.setDetectionTime(LocalDateTime.now());
                    
                    // 8. Save to database
                    logRepo.save(log);
                    logs.add(log);
                }
            }
        }

        return logs;
    }

    private double calculateSimilarityScore(Ticket ticket1, Ticket ticket2, DuplicateRule rule) {
        String matchType = rule.getMatchType();
        
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
                
                // Optional: More advanced keyword matching
                // For now, return 0.5 if subjects share any words
                String[] words1 = subject1.split("\\s+");
                String[] words2 = subject2.split("\\s+");
                for (String word1 : words1) {
                    for (String word2 : words2) {
                        if (word1.equals(word2) && word1.length() > 3) {
                            return 0.5;
                        }
                    }
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
        
        // Use the repository method with double underscore as specified
        return logRepo.findByTicket__ld(ticketId);
    }

    @Override
    public DuplicateDetectionLog getLog(Long id) {
        return logRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Duplicate detection log not found"));
    }
}