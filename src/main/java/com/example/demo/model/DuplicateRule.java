package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class DuplicateRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruleName;   // Rule name
    private String matchType;  // EXACT_MATCH, KEYWORD, SIMILARITY
    private Double threshold;  // Use Double to allow null

    public DuplicateRule() {}

    public DuplicateRule(Long id, String ruleName, String matchType, Double threshold) {
        this.id = id;
        this.ruleName = ruleName;
        this.matchType = matchType;
        this.threshold = threshold;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public String getMatchType() { return matchType; }
    public void setMatchType(String matchType) { this.matchType = matchType; }

    public Double getThreshold() { return threshold; }
    public void setThreshold(Double threshold) { this.threshold = threshold; }

    @Override
    public String toString() {
        return "DuplicateRule{" +
                "id=" + id +
                ", ruleName='" + ruleName + '\'' +
                ", matchType='" + matchType + '\'' +
                ", threshold=" + threshold +
                '}';
    }
}
