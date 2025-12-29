package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "duplicate_detection_logs")
public class DuplicateDetectionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
    
    @ManyToOne
    @JoinColumn(name = "duplicate_ticket_id")
    private Ticket duplicateTicket;
    
    private double matchScore;
    private LocalDateTime detectedAt;

    public DuplicateDetectionLog() {
        this.detectedAt = LocalDateTime.now();
    }

    public DuplicateDetectionLog(Ticket ticket, Ticket duplicateTicket, double matchScore) {
        this();
        this.ticket = ticket;
        this.duplicateTicket = duplicateTicket;
        this.matchScore = matchScore;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }
    
    public Ticket getDuplicateTicket() { return duplicateTicket; }
    public void setDuplicateTicket(Ticket duplicateTicket) { this.duplicateTicket = duplicateTicket; }
    
    public double getMatchScore() { return matchScore; }
    public void setMatchScore(double matchScore) { this.matchScore = matchScore; }
    
    public LocalDateTime getDetectedAt() { return detectedAt; }
    public void setDetectedAt(LocalDateTime detectedAt) { this.detectedAt = detectedAt; }
}