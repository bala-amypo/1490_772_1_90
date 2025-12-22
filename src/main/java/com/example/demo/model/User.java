package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String password;
    private String role;

    public User() {}

    public User(Long id, String fullName, String email, String password, String role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

2️⃣ TicketCategory.java
package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class TicketCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;
    private String description;

    public TicketCategory() {}

    public TicketCategory(Long id, String categoryName, String description) {
        this.id = id;
        this.categoryName = categoryName;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

3️⃣ Ticket.java
package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long categoryId;
    private String subject;
    private String description;
    private String status;

    public Ticket() {}

    public Ticket(Long id, Long userId, Long categoryId,
                  String subject, String description, String status) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.subject = subject;
        this.description = description;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

4️⃣ DuplicateRule.java
package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class DuplicateRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruleName;
    private String matchType;
    private Double threshold;

    public DuplicateRule() {}

    public DuplicateRule(Long id, String ruleName, String matchType, Double threshold) {
        this.id = id;
        this.ruleName = ruleName;
        this.matchType = matchType;
        this.threshold = threshold;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public String getMatchType() { return matchType; }
    public void setMatchType(String matchType) { this.matchType = matchType; }

    public Double getThreshold() { return threshold; }
    public void setThreshold(Double threshold) { this.threshold = threshold; }
}

5️⃣ DuplicateDetectionLog.java
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

✅ Final confirmation

✔