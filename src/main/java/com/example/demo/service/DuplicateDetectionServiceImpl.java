package com.example.demo.service.impl;

import com.example.demo.model.DuplicateDetectionLog;
import com.example.demo.model.Ticket;
import com.example.demo.model.DuplicateRule;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.DuplicateRuleRepository;
import com.example.demo.repository.DuplicateDetectionLogRepository;
import com.example.demo.service.DuplicateDetectionService;
import com.example.demo.util.TextSimilarityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DuplicateDetectionServiceImpl implements DuplicateDetectionService {
    
    private final TicketRepository ticketRepository;
    private final DuplicateRuleRepository ruleRepository;
    private final DuplicateDetectionLogRepository detectionLogRepository;
    
    public DuplicateDetectionServiceImpl(TicketRepository ticketRepository,
                                       DuplicateRuleRepository ruleRepository,
                                       DuplicateDetectionLogRepository detectionLogRepository) {
        this.ticketRepository = ticketRepository;
        this.ruleRepository = ruleRepository;
        this.detectionLogRepository = detectionLogRepository;
    }
    
    @Override
    public List<DuplicateDetectionLog> detectDuplicates(Long ticketId) {
        Ticket targetTicket = ticketRepository.findById(ticketId).orElse(null);
        if (targetTicket == null) {
            return List.of();
        }
        
        List<Ticket> openTickets = ticketRepository.findByStatus("OPEN");
        List<DuplicateRule> activeRules = ruleRepository.findAll();
        
        List<DuplicateDetectionLog> detectionLogs = openTickets.stream()
            .filter(candidate -> !candidate.getId().equals(ticketId))
            .map(candidate -> {
                Double similarityScore = calculateSimilarity(targetTicket, candidate, activeRules);
                if (similarityScore > 0) {
                    DuplicateDetectionLog log = new DuplicateDetectionLog(
                        ticketId, 
                        candidate.getId(), 
                        similarityScore
                    );
                    return detectionLogRepository.save(log);
                }
                return null;
            })
            .filter(log -> log != null)
            .collect(Collectors.toList());
        
        return detectionLogs;
    }
    
    private Double calculateSimilarity(Ticket target, Ticket candidate, List<DuplicateRule> rules) {
        double maxScore = 0.0;
        
        for (DuplicateRule rule : rules) {
            double score = 0.0;
            
            switch (rule.getMatchType()) {
                case "KEYWORD":
                    score = calculateKeywordMatch(target, candidate);
                    break;
                case "SIMILARITY":
                    score = TextSimilarityUtil.similarity(
                        target.getSubject() + " " + target.getDescription(),
                        candidate.getSubject() + " " + candidate.getDescription()
                    );
                    break;
                case "EXACT_MATCH":
                    score = calculateExactMatch(target, candidate);
                    break;
            }
            
            if (score >= rule.getThreshold() && score > maxScore) {
                maxScore = score;
            }
        }
        
        return maxScore;
    }
    
    private Double calculateKeywordMatch(Ticket target, Ticket candidate) {
        // Simple keyword matching implementation
        String targetText = (target.getSubject() + " " + target.getDescription()).toLowerCase();
        String candidateText = (candidate.getSubject() + " " + candidate.getDescription()).toLowerCase();
        
        String[] targetWords = targetText.split("\\s+");
        int matches = 0;
        
        for (String word : targetWords) {
            if (word.length() > 3 && candidateText.contains(word)) {
                matches++;
            }
        }
        
        return (double) matches / targetWords.length;
    }
    
    private Double calculateExactMatch(Ticket target, Ticket candidate) {
        return target.getSubject().equalsIgnoreCase(candidate.getSubject()) ? 1.0 : 0.0;
    }
    
    @Override
    public List<DuplicateDetectionLog> getLogsForTicket(Long ticketId) {
        return detectionLogRepository.findByTicketId(ticketId);
    }
    
    @Override
    public DuplicateDetectionLog getLog(Long id) {
        return detectionLogRepository.findById(id).orElse(null);
    }
}