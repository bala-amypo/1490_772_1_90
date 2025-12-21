package com.example.demo.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "duplicate_detection_logs")
public class DuplicateDetectionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ticketId;         // replaced Ticket reference with just an ID
    private Long matchedTicketId;  // replaced matchedTicket reference with just an ID
    private Double matchScore;

    private LocalDateTime detectedAt;

    @PrePersist
    void created() { detectedAt = LocalDateTime.now(); }

    public DuplicateDetectionLog() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTicketId() { return ticketId; }
    public void setTicketId(Long ticketId) { this.ticketId = ticketId; }

    public Long getMatchedTicketId() { return matchedTicketId; }
    public void setMatchedTicketId(Long matchedTicketId) { this.matchedTicketId = matchedTicketId; }

    public Double getMatchScore() { return matchScore; }
    public void setMatchScore(Double matchScore) { this.matchScore = matchScore; }

    public LocalDateTime getDetectedAt() { return detectedAt; }

    public DuplicateDetectionLog(Long id, Long ticketId, Long matchedTicketId, Double matchScore,
            LocalDateTime detectedAt) {
        this.id = id;
        this.ticketId = ticketId;
        this.matchedTicketId = matchedTicketId;
        this.matchScore = matchScore;
        this.detectedAt = detectedAt;
    }
}
