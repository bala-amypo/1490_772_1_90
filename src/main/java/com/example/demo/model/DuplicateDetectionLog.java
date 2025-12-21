package com.example.demo.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "duplicate_detection_logs")
public class DuplicateDetectionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Ticket matchedTicket;

    @Column(nullable = false)
    private Double matchScore;

    private LocalDateTime detectedAt;

    @PrePersist
    void created() { detectedAt = LocalDateTime.now(); }
    public DuplicateDetectionLog() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Ticket getTicket() { return ticket; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }
    public Ticket getMatchedTicket() { return matchedTicket; }
    public void setMatchedTicket(Ticket matchedTicket) { this.matchedTicket = matchedTicket; }
    public Double getMatchScore() { return matchScore; }
    public void setMatchScore(Double matchScore) { this.matchScore = matchScore; }
    public LocalDateTime getDetectedAt() { return detectedAt; }
    public DuplicateDetectionLog(Long id, Ticket ticket, Ticket matchedTicket, Double matchScore,
            LocalDateTime detectedAt) {
        this.id = id;
        this.ticket = ticket;
        this.matchedTicket = matchedTicket;
        this.matchScore = matchScore;
        this.detectedAt = detectedAt;
    }
    
}
