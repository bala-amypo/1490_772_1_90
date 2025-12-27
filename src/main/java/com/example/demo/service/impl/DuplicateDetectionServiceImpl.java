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

    public DuplicateDetectionServiceImpl(
            TicketRepository ticketRepository,
            DuplicateRuleRepository ruleRepository,
            DuplicateDetectionLogRepository logRepository
    ) {
        this.ticketRepository = ticketRepository;
        this.ruleRepository = ruleRepository;
        this.logRepository = logRepository;
    }

    @Override
    public List<DuplicateDetectionLog> detectDuplicates(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        List<DuplicateRule> rules = ruleRepository.findAll();
        List<Ticket> openTickets = ticketRepository.findByStatus("OPEN");
        List<DuplicateDetectionLog> duplicates = new ArrayList<>();

        for (DuplicateRule rule : rules) {
            for (Ticket otherTicket : openTickets) {

                // skip self comparison
                if (Objects.equals(ticket.getId(), otherTicket.getId())) {
                    continue;
                }

                double score = calculateMatchScore(ticket, otherTicket, rule);

                if (score >= rule.getThreshold()) {
                    DuplicateDetectionLog log =
                            new DuplicateDetectionLog(ticket, otherTicket, score);
                    logRepository.save(log);
                    duplicates.add(log);
                }
            }
        }
        return duplicates;
    }

    private double calculateMatchScore(Ticket t1, Ticket t2, DuplicateRule rule) {

        String subject1 = t1.getSubject() == null ? "" : t1.getSubject().trim();
        String subject2 = t2.getSubject() == null ? "" : t2.getSubject().trim();

        String desc1 = t1.getDescription() == null ? "" : t1.getDescription().trim();
        String desc2 = t2.getDescription() == null ? "" : t2.getDescription().trim();

        switch (rule.getMatchType()) {

            case "EXACT_MATCH":
                // ✅ Exact Match = SUBJECT ONLY, case-insensitive
                return subject1.equalsIgnoreCase(subject2) ? 1.0 : 0.0;

            case "KEYWORD":
            case "SIMILARITY":
                // ✅ Keyword & Similarity use subject + description
                String text1 = (subject1 + " " + desc1).trim();
                String text2 = (subject2 + " " + desc2).trim();
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
