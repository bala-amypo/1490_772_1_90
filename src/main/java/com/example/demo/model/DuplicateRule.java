package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "duplicate_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuplicateRule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Rule name is required")
    @Size(min = 3, max = 50, message = "Rule name must be between 3 and 50 characters")
    @Column(name = "rule_name", nullable = false, unique = true)
    private String ruleName;
    
    @NotBlank(message = "Rule description is required")
    @Size(min = 10, message = "Rule description must be at least 10 characters")
    @Column(nullable = false, length = 500)
    private String description;
    
    @NotBlank(message = "Match type is required")
    @Pattern(regexp = "EXACT_MATCH|KEYWORD|SIMILARITY", 
             message = "Match type must be EXACT_MATCH, KEYWORD, or SIMILARITY")
    @Column(name = "match_type", nullable = false)
    private String matchType;
    
    @NotNull(message = "Similarity threshold is required")
    @DecimalMin(value = "0.0", message = "Similarity threshold must be at least 0.0")
    @DecimalMax(value = "1.0", message = "Similarity threshold must be at most 1.0")
    @Column(name = "similarity_threshold", nullable = false)
    private Double similarityThreshold;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructor without id for creating new rules
    public DuplicateRule(String ruleName, String description, String matchType, 
                         Double similarityThreshold, Boolean isActive) {
        this.ruleName = ruleName;
        this.description = description;
        this.matchType = matchType;
        this.similarityThreshold = similarityThreshold;
        this.isActive = isActive;
    }
}