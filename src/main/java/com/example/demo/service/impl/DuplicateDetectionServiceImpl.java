package com.example.demo.service.impl;

import com.example.demo.model.Ticket;
import com.example.demo.model.DuplicateRule;
import com.example.demo.model.DuplicateDetectionLog;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.DuplicateRuleRepository;
import com.example.demo.repository.DuplicateDetectionLogRepository;
import com.example.demo.service.DuplicateDetectionService;
import com.example.demo.util.TextSimilarityUtil;
import java.util.List;
import java.util.ArrayList;

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
                if (!otherTicket.getId().equals(ticket.getId())) {
                    boolean isDuplicate = false;
                    double score = 0.0;

                    switch (rule.getMatchType()) {
                        case "EXACT_MATCH":
                            if (ticket.getSubject() != null && otherTicket.getSubject() != null &&
                                ticket.getSubject().equalsIgnoreCase(otherTicket.getSubject())) {
                                isDuplicate = true;
                                score = 1.0;
                            }
                            break;
                        case "KEYWORD":
                            String[] keywords1 = (ticket.getSubject() + " " + ticket.getDescription()).toLowerCase().split("\\s+");
                            String[] keywords2 = (otherTicket.getSubject() + " " + otherTicket.getDescription()).toLowerCase().split("\\s+");
                            int matches = 0;
                            for (String kw1 : keywords1) {
                                for (String kw2 : keywords2) {
                                    if (kw1.equals(kw2)) matches++;
                                }
                            }
                            score = (double) matches / Math.max(keywords1.length, keywords2.length);
                            isDuplicate = score >= rule.getThreshold();
                            break;
                        case "SIMILARITY":
                            String text1 = (ticket.getDescription() != null ? ticket.getDescription() : "");
                            String text2 = (otherTicket.getDescription() != null ? otherTicket.getDescription() : "");
                            score = TextSimilarityUtil.similarity(text1, text2);
                            isDuplicate = score >= rule.getThreshold();
                            break;
                    }

                    if (isDuplicate) {
                        DuplicateDetectionLog log = new DuplicateDetectionLog(ticket, otherTicket, score);
                        logRepository.save(log);
                        duplicates.add(log);
                    }
                }
            }
        }
        return duplicates;
    }

    @Override
    public List<DuplicateDetectionLog> getLogsForTicket(Long ticketId) {
        return logRepository.findByTicket_Id(ticketId);
    }
}