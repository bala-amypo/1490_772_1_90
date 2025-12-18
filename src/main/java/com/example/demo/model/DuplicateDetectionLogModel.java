package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class DuplicateDetectionLogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Ticket on which detection is run
    @ManyToOne(optional = false)
    @JoinColumn(name = "ticket_id")
    private TicketModel ticket;

    // ✅ Matched duplicate ticket
    @ManyToOne(optional = false)
    @JoinColumn(name = "matched_ticket_id")
    private TicketModel matchedTicket;

    // ✅ Rule used for detection
    @ManyToOne
    @JoinColumn(name = "rule_id")
    private DuplicateRuleModel rule;

    @Column(nullable = false)
    private Double matchScore;

    private LocalDateTime detectedAt;

    @PrePersist
    public void onCreate() {
        detectedAt = LocalDateTime.now();
    }

    // getters & setters

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

    public DuplicateRuleModel getRule() {
        return rule;
    }

    public void setRule(DuplicateRuleModel rule) {
        this.rule = rule;
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

    public DuplicateDetectionLogModel(
            Long id,
            TicketModel ticket,
            TicketModel matchedTicket,
            DuplicateRuleModel rule,
            Double matchScore,
            LocalDateTime detectedAt) {

        this.id = id;
        this.ticket = ticket;
        this.matchedTicket = matchedTicket;
        this.rule = rule;
        this.matchScore = matchScore;
        this.detectedAt = detectedAt;
    }

    public DuplicateDetectionLogModel() {
    }
}
