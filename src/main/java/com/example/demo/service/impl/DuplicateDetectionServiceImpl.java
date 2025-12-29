package com.example.demo.service.impl;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DuplicateDetectionService;
import com.example.demo.util.TextSimilarityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<DuplicateDetectionLog> getLogsForTicket(Long ticketId) {
        return logRepository.findByTicket_Id(ticketId);
    }

    @Override
    public List<DuplicateDetectionLog> detectDuplicates(Long ticketId) {
        Ticket baseTicket = ticketRepository.findById(ticketId).orElse(null);
        if (baseTicket == null) return new ArrayList<>();
        
        List<DuplicateRule> rules = ruleRepository.findAll();
        if (rules.isEmpty()) return new ArrayList<>();
        
        List<Ticket> openTickets = ticketRepository.findByStatus("OPEN");
        List<DuplicateDetectionLog> logs = new ArrayList<>();
        
        for (Ticket candidateTicket : openTickets) {
            if (candidateTicket.getId() != null && baseTicket.getId() != null && 
                candidateTicket.getId().equals(baseTicket.getId())) continue;
            
            for (DuplicateRule rule : rules) {
                double score = calculateMatchScore(baseTicket, candidateTicket, rule);
                
                if (score >= rule.getThreshold()) {
                    DuplicateDetectionLog log = new DuplicateDetectionLog(baseTicket, candidateTicket, score);
                    log.setDetectedAt(LocalDateTime.now());
                    log = logRepository.save(log);
                    logs.add(log);
                }
            }
        }
        
        return logs;
    }

    private double calculateMatchScore(Ticket baseTicket, Ticket candidateTicket, DuplicateRule rule) {
        String matchType = rule.getMatchType();
        
        if ("EXACT_MATCH".equals(matchType)) {
            String baseSubject = baseTicket.getSubject() != null ? baseTicket.getSubject() : "";
            String candidateSubject = candidateTicket.getSubject() != null ? candidateTicket.getSubject() : "";
            return baseSubject.equalsIgnoreCase(candidateSubject) ? 1.0 : 0.0;
        } else if ("KEYWORD".equals(matchType)) {
            String baseText = combineSubjectAndDescription(baseTicket);
            String candidateText = combineSubjectAndDescription(candidateTicket);
            return calculateKeywordSimilarity(baseText, candidateText);
        } else if ("SIMILARITY".equals(matchType)) {
            String baseDesc = baseTicket.getDescription() != null ? baseTicket.getDescription() : "";
            String candidateDesc = candidateTicket.getDescription() != null ? candidateTicket.getDescription() : "";
            return TextSimilarityUtil.similarity(baseDesc, candidateDesc);
        }
        
        return 0.0;
    }

    private String combineSubjectAndDescription(Ticket ticket) {
        String subject = ticket.getSubject() != null ? ticket.getSubject() : "";
        String description = ticket.getDescription() != null ? ticket.getDescription() : "";
        return (subject + " " + description).trim();
    }

    private double calculateKeywordSimilarity(String text1, String text2) {
        if (text1.isEmpty() || text2.isEmpty()) return 0.0;
        
        Set<String> words1 = extractWords(text1);
        Set<String> words2 = extractWords(text2);
        
        if (words1.isEmpty() || words2.isEmpty()) return 0.0;
        
        Set<String> intersection = new HashSet<>(words1);
        intersection.retainAll(words2);
        
        Set<String> union = new HashSet<>(words1);
        union.addAll(words2);
        
        return union.isEmpty() ? 0.0 : (double) intersection.size() / union.size();
    }

    private Set<String> extractWords(String text) {
        Set<String> words = new HashSet<>();
        String[] tokens = text.toLowerCase().split("\\s+");
        for (String token : tokens) {
            if (!token.isBlank()) {
                words.add(token);
            }
        }
        return words;
    }

    public DuplicateDetectionLog getLog(Long id) {
        return logRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Log not found"));
    }
}