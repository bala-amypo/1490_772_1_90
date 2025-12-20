package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.DuplicateDetectionLog;
import com.example.demo.model.DuplicateRule;
import com.example.demo.model.Ticket;
import com.example.demo.repository.DuplicateDetectionLogRepository;
import com.example.demo.repository.DuplicateRuleRepository;
import com.example.demo.repository.TicketRepository;

@Service
public class DuplicateDetectionServiceImpl implements DuplicateDetectionService {

    private final TicketRepository ticketRepo;
    private final DuplicateRuleRepository ruleRepo;
    private final DuplicateDetectionLogRepository logRepo;

    // Constructor injection ✅
    public DuplicateDetectionServiceImpl(TicketRepository ticketRepo,
                                         DuplicateRuleRepository ruleRepo,
                                         DuplicateDetectionLogRepository logRepo) {
        this.ticketRepo = ticketRepo;
        this.ruleRepo = ruleRepo;
        this.logRepo = logRepo;
    }

    @Override
    public List<DuplicateDetectionLog> detectDuplicates(Long ticketId) {
        Ticket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("ticket not found"));

        List<Ticket> openTickets = ticketRepo.findByStatus("OPEN");
        List<DuplicateRule> rules = ruleRepo.findAll();
        List<DuplicateDetectionLog> logs = new ArrayList<>();

        for (Ticket other : openTickets) {
            if (other.getId().equals(ticket.getId())) continue;

            for (DuplicateRule rule : rules) {
                double score = 0.0;
                switch (rule.getMatchType()) {
                    case "EXACT_MATCH":
                        if (ticket.getDescription().equalsIgnoreCase(other.getDescription())) score = 1.0;
                        break;
                    case "KEYWORD":
                        if (ticket.getSubject().equalsIgnoreCase(other.getSubject())) score = 1.0;
                        break;
                    case "SIMILARITY":
                        score = similarity(ticket.getDescription(), other.getDescription());
                        break;
                }

                if (score >= rule.getThreshold()) {
                    DuplicateDetectionLog log = new DuplicateDetectionLog();
                    log.setTicket(ticket);
                    log.setMatchedTicket(other);
                    log.setMatchScore(score);
                    logRepo.save(log);
                    logs.add(log);
                }
            }
        }

        return logs;
    }

    @Override
    public List<DuplicateDetectionLog> getLogsForTicket(Long ticketId) {
        if (!ticketRepo.existsById(ticketId))
            throw new ResourceNotFoundException("ticket not found");
        return logRepo.findByTicket_Id(ticketId);
    }

    @Override
    public DuplicateDetectionLog getLog(Long id) {
        return logRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("log not found"));
    }

    private double similarity(String s1, String s2) {
        if (s1 == null || s2 == null) return 0.0;
        Set<String> w1 = new HashSet<>(List.of(s1.toLowerCase().split("\\s+")));
        Set<String> w2 = new HashSet<>(List.of(s2.toLowerCase().split("\\s+")));
        Set<String> inter = new HashSet<>(w1); inter.retainAll(w2);
        Set<String> union = new HashSet<>(w1); union.addAll(w2);
        return union.isEmpty() ? 0.0 : (double) inter.size() / union.size();
    }
}
