package com.example.demo.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "duplicate_rules")
public class DuplicateRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String ruleName;

    @NotBlank
    @Column(nullable = false)
    private String matchType; // KEYWORD / SIMILARITY / EXACT_MATCH

    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double threshold;

    private LocalDateTime createdAt;

    @PrePersist
    void created() { createdAt = LocalDateTime.now(); }

    public DuplicateRule() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    public String getMatchType() { return matchType; }
    public void setMatchType(String matchType) { this.matchType = matchType; }
    public Double getThreshold() { return threshold; }
    public void setThreshold(Double threshold) { this.threshold = threshold; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
