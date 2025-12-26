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
        List<DuplicateDetectionLog> duplicates = new ArrayList<>();

        for (DuplicateRule rule : rules) {
            for (Ticket otherTicket : openTickets) {

                // skip self
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

    private double calculateMatchScore(Ticket ticket1, Ticket ticket2, DuplicateRule rule) {

        String text1 =
                (ticket1.getSubject() != null ? ticket1.getSubject() : "") + " " +
                (ticket1.getDescription() != null ? ticket1.getDescription() : "");

        String text2 =
                (ticket2.getSubject() != null ? ticket2.getSubject() : "") + " " +
                (ticket2.getDescription() != null ? ticket2.getDescription() : "");

        switch (rule.getMatchType()) {

            case "EXACT_MATCH":
                String normalized1 = text1.trim().toLowerCase();
                String normalized2 = text2.trim().toLowerCase();
                return normalized1.equals(normalized2) ? 1.0 : 0.0;

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
