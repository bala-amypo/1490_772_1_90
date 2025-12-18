package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "duplicate_rule")
public class DuplicateRuleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fieldName;

    @Column(nullable = false)
    private double threshold;

    // ✅ RELATIONSHIP CODE (ADD THIS)
    @OneToMany(mappedBy = "rule")
    private List<DuplicateDetectionLogModel> detectionLogs;

    // getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public List<DuplicateDetectionLogModel> getDetectionLogs() {
        return detectionLogs;
    }

    public void setDetectionLogs(List<DuplicateDetectionLogModel> detectionLogs) {
        this.detectionLogs = detectionLogs;
    }

    // constructors
    public DuplicateRuleModel(Long id, String fieldName, double threshold) {
        this.id = id;
        this.fieldName = fieldName;
        this.threshold = threshold;
    }

    public DuplicateRuleModel() {
    }
}
