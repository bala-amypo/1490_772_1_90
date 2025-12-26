package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DuplicateRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String matchType; // "EXACT_MATCH", "KEYWORD", "SIMILARITY"
    private double threshold;

    private LocalDateTime createdAt;

    public DuplicateRule() {
        this.createdAt = LocalDateTime.now();
    }

    public DuplicateRule(String name, String matchType, double threshold) {
        this.name = name;
        this.matchType = matchType;
        this.threshold = threshold;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMatchType() { return matchType; }
    public void setMatchType(String matchType) { this.matchType = matchType; }

    public double getThreshold() { return threshold; }
    public void setThreshold(double threshold) { this.threshold = threshold; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    /**
     * Checks if two strings match according to the rule type.
     */
    public boolean matches(String a, String b) {
        if (a == null || b == null) return false;

        switch (this.matchType) {
            case "EXACT_MATCH":
                // Case-insensitive exact match
                return a.trim().equalsIgnoreCase(b.trim());

            case "KEYWORD":
            case "SIMILARITY":
                // For other types, you would use your similarity utility
                // Here just a placeholder, real implementation uses TextSimilarityUtil
                return a.trim().equals(b.trim());

            default:
                return false;
        }
    }
}
