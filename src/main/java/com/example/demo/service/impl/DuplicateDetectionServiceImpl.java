package com.example.demo.service.impl;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DuplicateDetectionService;
import com.example.demo.util.TextSimilarityUtil;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DuplicateDetectionServiceImpl implements DuplicateDetectionService {
    private final TicketRepository ticketRepository;
    private final DuplicateRuleRepository ruleRepository;
    private final DuplicateDetectionLogRepository logRepository;

    public DuplicateDetectionServiceImpl(TicketRepository ticketRepository, DuplicateRuleRepository ruleRepository, DuplicateDetectionLogRepository logRepository) {
        this.ticketRepository = ticketRepository;
        this.ruleRepository = ruleRepository;
        this.logRepository = logRepository;
    }

    @Override
    public List<DuplicateDetectionLog> detectDuplicates(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        List<DuplicateRule> rules = ruleRepository.findAll();
        // Fetching OPEN tickets to compare against
        List<Ticket> openTickets = ticketRepository.findByStatus("OPEN");
        List<DuplicateDetectionLog> duplicates = new ArrayList<>();

        for (DuplicateRule rule : rules) {
            for (Ticket otherTicket : openTickets) {
                // Do not compare the ticket with itself
                if (!Objects.equals(ticket.getId(), otherTicket.getId())) {
                    double score = calculateMatchScore(ticket, otherTicket, rule);
                    if (score >= rule.getThreshold()) {
                        DuplicateDetectionLog log = new DuplicateDetectionLog(ticket, otherTicket, score);
                        logRepository.save(log);
                        duplicates.add(log);
                    }
                }
            }
        }
        return duplicates;
    }

    private double calculateMatchScore(Ticket ticket1, Ticket ticket2, DuplicateRule rule) {
        // Construct full text strings for comparison
        String s1 = ticket1.getSubject() != null ? ticket1.getSubject().trim() : "";
        String d1 = ticket1.getDescription() != null ? ticket1.getDescription().trim() : "";
        String s2 = ticket2.getSubject() != null ? ticket2.getSubject().trim() : "";
        String d2 = ticket2.getDescription() != null ? ticket2.getDescription().trim() : "";

        String text1 = (s1 + " " + d1).trim();
        String text2 = (s2 + " " + d2).trim();

        switch (rule.getMatchType()) {
            case "EXACT_MATCH":
                // FIX: Compare the combined text (Subject + Description) case-insensitively
                // This ensures that "Error" and "error" are treated as matches.
                return text1.equalsIgnoreCase(text2) ? 1.0 : 0.0;

            case "KEYWORD":
            case "SIMILARITY":
                return TextSimilarityUtil.similarity(text1, text2);
                
            default:
                return 0.0;
        }
    }

    @Override
    public List<DuplicateDetectionLog> getLogsForTicket(Long ticketId) {
        return logRepository.findByTicket_Id(ticketId);
    }
}