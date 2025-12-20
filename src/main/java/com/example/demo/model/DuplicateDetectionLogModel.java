package com.example.demo.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class DuplicateDetectionLogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private TicketModel ticket;

    @ManyToOne(optional = false)
    private TicketModel matchedTicket;

    @Column(nullable = false)
    private Double matchScore;

    private LocalDateTime detectedAt;

    @PrePersist
    public void onCreate() {
        detectedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicketModel getTicket() {
        return ticket;
    }

    public void setTicket(TicketModel ticket) {
        this.ticket = ticket;
    }

    public TicketModel getMatchedTicket() {
        return matchedTicket;
    }

    public void setMatchedTicket(TicketModel matchedTicket) {
        this.matchedTicket = matchedTicket;
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

    public DuplicateDetectionLogModel(Long id, TicketModel ticket, TicketModel matchedTicket, Double matchScore,
            LocalDateTime detectedAt) {
        this.id = id;
        this.ticket = ticket;
        this.matchedTicket = matchedTicket;
        this.matchScore = matchScore;
        this.detectedAt = detectedAt;
    }

    public DuplicateDetectionLogModel() {
    }
    
}
