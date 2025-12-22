package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class DuplicateDetectionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ticketId;
    private Long matchedTicketId;
    private Double matchScore;

    public DuplicateDetectionLog() {}

    public DuplicateDetectionLog(Long id, Long ticketId,
                                 Long matchedTicketId, Double matchScore) {
        this.id = id;
        this.ticketId = ticketId;
        this.matchedTicketId = matchedTicketId;
        this.matchScore = matchScore;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTicketId() { return ticketId; }
    public void setTicketId(Long ticketId) { this.ticketId = ticketId; }

    public Long getMatchedTicketId() { return matchedTicketId; }
    public void setMatchedTicketId(Long matchedTicketId) { this.matchedTicketId = matchedTicketId; }

    public Double getMatchScore() { return matchScore; }
    public void setMatchScore(Double matchScore) { this.matchScore = matchScore; }
}
