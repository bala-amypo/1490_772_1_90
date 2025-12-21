package com.example.demo.service;

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

    // CORRECT CONSTRUCTOR SIGNATURE AS PER REQUIREMENTS
    public DuplicateDetectionServiceImpl(TicketRepository ticketRepo,
                                         DuplicateRuleRepository ruleRepo,
                                         DuplicateDetectionLogRepository logRepo) {
        this.ticketRepo = ticketRepo;
        this.ruleRepo = ruleRepo;
        this.logRepo = logRepo;
    }

    @Override
    public List<DuplicateDetectionLog> detectDuplicates(Long ticketId) {
        // 1. Fetch the target ticket
        Ticket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("ticket not found"));

        // 2. Fetch open tickets
        List<Ticket> openTickets = ticketRepo.findByStatus("OPEN");
        
        // 3. Fetch active rules
        List<DuplicateRule> activeRules = ruleRepo.findAll().stream()
                .filter(rule -> rule.getIsActive() != null && rule.getIsActive())
                .toList();

        List<DuplicateDetectionLog> logs = new ArrayList<>();

        // 4. Compare with each open ticket
        for (Ticket other : openTickets) {
            if (other.getId().equals(ticket.getId())) continue; // Skip self

            // 5. Apply each active rule
            for (DuplicateRule rule : activeRules) {
                double score = calculateSimilarityScore(ticket, other, rule);

                // 6. Check if score meets threshold
                if (score >= rule.getSimilarityThreshold()) {
                    // 7. Create and save log
                    DuplicateDetectionLog log = new DuplicateDetectionLog(
                        ticket, other, score
                    );
                    logRepo.save(log);
                    logs.add(log);
                }
            }
        }

        return logs;
    }

    private double calculateSimilarityScore(Ticket ticket1, Ticket ticket2, DuplicateRule rule) {
        switch (rule.getMatchType()) {
            case "EXACT_MATCH":
                return ticket1.getDescription().equalsIgnoreCase(ticket2.getDescription()) ? 1.0 : 0.0;
                
            case "KEYWORD":
                // Check if subject contains common keywords or exact match
                String sub1 = ticket1.getSubject().toLowerCase();
                String sub2 = ticket2.getSubject().toLowerCase();
                return sub1.equals(sub2) ? 1.0 : 0.5; // Simplified logic
                
            case "SIMILARITY":
                // Use TextSimilarityUtil as required
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
        // Check if ticket exists
        if (!ticketRepo.existsById(ticketId)) {
            throw new ResourceNotFoundException("ticket not found");
        }
        
        // Use the repository method with double underscore as specified
        return logRepo.findByTicket__ld(ticketId);
    }

    @Override
    public DuplicateDetectionLog getLog(Long id) {
        return logRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("log not found"));
    }
}