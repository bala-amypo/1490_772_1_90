package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DuplicateRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruleName;
    private String matchType; // EXACT_MATCH, KEYWORD, SIMILARITY
    private double threshold;

    // Default constructor
    public DuplicateRule() {}

    // Constructor used in tests
    public DuplicateRule(String ruleName, String matchType, double threshold) {
        this.ruleName = ruleName;
        this.matchType = matchType;
        this.threshold = threshold;
    }

    // Full constructor with ID
    public DuplicateRule(Long id, String ruleName, String matchType, double threshold) {
        this.id = id;
        this.ruleName = ruleName;
        this.matchType = matchType;
        this.threshold = threshold;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public String getMatchType() { return matchType; }
    public void setMatchType(String matchType) { this.matchType = matchType; }

    public double getThreshold() { return threshold; }
    public void setThreshold(double threshold) { this.threshold = threshold; }
}
