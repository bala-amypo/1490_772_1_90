package com.example.demo.service;

import java.util.*;
import org.springframework.stereotype.Service;
import com.example.demo.model.*;
import com.example.demo.repository.*;

@Service
public class DuplicateDetectionServiceImpl implements DuplicateDetectionService {

    private final TicketRepository ticketRepo;
    private final DuplicateRuleRepository ruleRepo;
    private final DuplicateDetectionLogRepository logRepo;

    public DuplicateDetectionServiceImpl(
            TicketRepository ticketRepo,
            DuplicateRuleRepository ruleRepo,
            DuplicateDetectionLogRepository logRepo) {
        this.ticketRepo = ticketRepo;
        this.ruleRepo = ruleRepo;
        this.logRepo = logRepo;
    }

    @Override
    public List<DuplicateDetectionLog> detectDuplicates(Long ticketId) {
        Optional<Ticket> optTicket = ticketRepo.findById(ticketId);
        if (optTicket.isEmpty()) return Collections.emptyList();
        Ticket ticket = optTicket.get();

        List<Ticket> openTickets = ticketRepo.findByStatus("OPEN");
        List<DuplicateRule> rules = ruleRepo.findAll();
        List<DuplicateDetectionLog> logs = new ArrayList<>();

        for (Ticket other : openTickets) {
            if (other.getId().equals(ticket.getId())) continue;

            for (DuplicateRule rule : rules) {
                double score = 0.0;

                switch (rule.getMatchType()) {
                    case "EXACT_MATCH":
                        if (ticket.getDescription().equalsIgnoreCase(other.getDescription()))
                            score = 1.0;
                        break;
                    case "KEYWORD":
                        if (ticket.getSubject().equalsIgnoreCase(other.getSubject()))
                            score = 1.0;
                        break;
                }

                if (score >= rule.getThreshold()) {
                    DuplicateDetectionLog log = new DuplicateDetectionLog();
                    log.setTicket(ticket);
                    log.setMatchedTicket(other);
                    log.setMatchScore(score);
                    logRepo.save(log);
                    logs.add(log);
                    break;
                }
            }
        }
        return logs;
    }

    @Override
    public List<DuplicateDetectionLog> getLogsForTicket(Long ticketId) {
        return logRepo.findByTicket_Id(ticketId);
    }

    @Override
    public DuplicateDetectionLog getLog(Long id) {
        return logRepo.findById(id).orElse(null);
    }
}
