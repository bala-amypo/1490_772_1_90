package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DuplicateDetectionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ticketId;
    private Long matchedTicketId;
    private Double matchScore;

    private LocalDateTime detectedAt = LocalDateTime.now();

    public DuplicateDetectionLog() {
    }
    public DuplicateDetectionLog(Ticket t1, Ticket t2, double score) {
        this.ticketId = t1.getId();
        this.matchedTicketId = t2.getId();
        this.matchScore = score;
    }

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
}
