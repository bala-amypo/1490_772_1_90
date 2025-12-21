package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "duplicate_detection_logs")
public class DuplicateDetectionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long ticketId;
    
    private Long matchedTicketId;
    
    private Double matchScore;
    
    private LocalDateTime detectedAt;
    
    // Default Constructor
    public DuplicateDetectionLog() {
    }
    
    // Parameterized Constructor
    public DuplicateDetectionLog(Long ticketId, Long matchedTicketId, Double matchScore) {
        this.ticketId = ticketId;
        this.matchedTicketId = matchedTicketId;
        this.matchScore = matchScore;
    }
    
    @PrePersist
    protected void onCreate() {
        detectedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getTicketId() {
        return ticketId;
    }
    
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    
    public Long getMatchedTicketId() {
        return matchedTicketId;
    }
    
    public void setMatchedTicketId(Long matchedTicketId) {
        this.matchedTicketId = matchedTicketId;
    }
    
    public Double getMatchScore() {
        return matchScore;
    }
    
    public void setMatchScore(Double matchScore) {
        this.matchScore = matchScore;
    }
    
    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }
    
    public void setDetectedAt(LocalDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }
}