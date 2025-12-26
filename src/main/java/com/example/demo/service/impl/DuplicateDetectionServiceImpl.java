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
        List<Ticket> openTickets = ticketRepository.findByStatus("OPEN");
        List<DuplicateDetectionLog> duplicates = new ArrayList<>();

        for (DuplicateRule rule : rules) {
            for (Ticket otherTicket : openTickets) {
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
        String text1 = (ticket1.getSubject() != null ? ticket1.getSubject() : "") + " " + 
                      (ticket1.getDescription() != null ? ticket1.getDescription() : "");
        String text2 = (ticket2.getSubject() != null ? ticket2.getSubject() : "") + " " + 
                      (ticket2.getDescription() != null ? ticket2.getDescription() : "");

        switch (rule.getMatchType()) {
            case "EXACT_MATCH":
                // For exact match, only compare subjects case-insensitively
                String subject1 = ticket1.getSubject() != null ? ticket1.getSubject() : "";
                String subject2 = ticket2.getSubject() != null ? ticket2.getSubject() : "";
                return subject1.equalsIgnoreCase(subject2) ? 1.0 : 0.0;
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