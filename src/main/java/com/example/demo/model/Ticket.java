package com.example.demo.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private String description;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id") // Creates a foreign key column in the Ticket table
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id") // Creates a foreign key column in the Ticket table
    private TicketCategory category;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Ticket() {}

    public Ticket(Long id, String subject, String description, String status, User user, TicketCategory category) {
        this.id = id;
        this.subject = subject;
        this.description = description;
        this.status = status;
        this.user = user;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public TicketCategory getCategory() { return category; }
    public void setCategory(TicketCategory category) { this.category = category; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}