package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "duplicate_detection_logs")
public class DuplicateDetectionLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ticket1_id", nullable = false)
    @NotNull(message = "Ticket 1 is required")
    private Ticket ticket1;
    
    @ManyToOne
    @JoinColumn(name = "ticket2_id", nullable = false)
    @NotNull(message = "Ticket 2 is required")
    private Ticket ticket2;
    
    @NotNull(message = "Similarity score is required")
    @DecimalMin(value = "0.0", message = "Similarity score must be at least 0.0")
    @DecimalMax(value = "1.0", message = "Similarity score must be at most 1.0")
    @Column(name = "similarity_score", nullable = false)
    private Double similarityScore;
    
    @Column(name = "detection_time")
    private LocalDateTime detectionTime;
    
    // Constructors
    public DuplicateDetectionLog() {}
    
    public DuplicateDetectionLog(Ticket ticket1, Ticket ticket2, Double similarityScore) {
        this.ticket1 = ticket1;
        this.ticket2 = ticket2;
        this.similarityScore = similarityScore;
    }
    
    public DuplicateDetectionLog(Long id, Ticket ticket1, Ticket ticket2, 
                                 Double similarityScore, LocalDateTime detectionTime) {
        this.id = id;
        this.ticket1 = ticket1;
        this.ticket2 = ticket2;
        this.similarityScore = similarityScore;
        this.detectionTime = detectionTime;
    }
    
    @PrePersist
    protected void onCreate() {
        detectionTime = LocalDateTime.now();
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Ticket getTicket1() { return ticket1; }
    public void setTicket1(Ticket ticket1) { this.ticket1 = ticket1; }
    
    public Ticket getTicket2() { return ticket2; }
    public void setTicket2(Ticket ticket2) { this.ticket2 = ticket2; }
    
    public Double getSimilarityScore() { return similarityScore; }
    public void setSimilarityScore(Double similarityScore) { 
        this.similarityScore = similarityScore; 
    }
    
    public LocalDateTime getDetectionTime() { return detectionTime; }
    public void setDetectionTime(LocalDateTime detectionTime) { 
        this.detectionTime = detectionTime; 
    }
}