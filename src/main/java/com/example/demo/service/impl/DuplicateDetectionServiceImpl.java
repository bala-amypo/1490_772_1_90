package com.example.demo.service.impl;

import com.example.demo.model.DuplicateDetectionLog;
import com.example.demo.model.DuplicateRule;
import com.example.demo.model.Ticket;
import com.example.demo.repository.DuplicateDetectionLogRepository;
import com.example.demo.repository.DuplicateRuleRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.service.DuplicateDetectionService;
import com.example.demo.util.TextSimilarityUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        List<DuplicateDetectionLog> results = new ArrayList<>();

        for (DuplicateRule rule : rules) {
            for (Ticket other : openTickets) {

                // skip self
                if (Objects.equals(ticket.getId(), other.getId())) {
                    continue;
                }

                double score = calculateMatchScore(ticket, other, rule);

                if (score >= rule.getThreshold()) {
                    DuplicateDetectionLog log =
                            new DuplicateDetectionLog(ticket, other, score);
                    logRepository.save(log);
                    results.add(log);
                }
            }
        }

        return results;
    }

    private double calculateMatchScore(Ticket t1, Ticket t2, DuplicateRule rule) {

        String subject1 = normalize(t1.getSubject());
        String subject2 = normalize(t2.getSubject());

        String desc1 = normalize(t1.getDescription());
        String desc2 = normalize(t2.getDescription());

        String matchType = rule.getMatchType() == null
                ? ""
                : rule.getMatchType().trim().toUpperCase();

        switch (matchType) {

            case "EXACT":
            case "EXACT_MATCH":
                // strict, case-insensitive subject equality
                return subject1.equals(subject2) ? 1.0 : 0.0;

            case "KEYWORD":
            case "SIMILARITY":
                String text1 = (subject1 + " " + desc1).trim();
                String text2 = (subject2 + " " + desc2).trim();
                return TextSimilarityUtil.similarity(text1, text2);

            default:
                return 0.0;
        }
    }

    private String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }

    @Override
    public List<DuplicateDetectionLog> getLogsForTicket(Long ticketId) {
        return logRepository.findByTicket_Id(ticketId);
    }
}

