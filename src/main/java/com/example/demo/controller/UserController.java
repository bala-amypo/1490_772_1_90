✅ 1. USER
UserRepository
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

UserService
package com.example.demo.service;

import java.util.List;
import com.example.demo.model.User;

public interface UserService {
    User save(User user);
    List<User> getAll();
    User getById(Long id);
}

UserServiceImpl
package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    public User save(User user) {
        return repo.save(user);
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public User getById(Long id) {
        return repo.findById(id).orElse(null);
    }
}

UserController
package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public User save(@RequestBody User user) {
        return service.save(user);
    }

    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return service.getById(id);
    }
}

✅ 2. TICKET CATEGORY
TicketCategoryRepository
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.TicketCategory;

public interface TicketCategoryRepository extends JpaRepository<TicketCategory, Long> {
}

TicketCategoryService
package com.example.demo.service;

import java.util.List;
import com.example.demo.model.TicketCategory;

public interface TicketCategoryService {
    TicketCategory save(TicketCategory category);
    List<TicketCategory> getAll();
}

TicketCategoryServiceImpl
package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.model.TicketCategory;
import com.example.demo.repository.TicketCategoryRepository;
import com.example.demo.service.TicketCategoryService;

@Service
public class TicketCategoryServiceImpl implements TicketCategoryService {

    private final TicketCategoryRepository repo;

    public TicketCategoryServiceImpl(TicketCategoryRepository repo) {
        this.repo = repo;
    }

    public TicketCategory save(TicketCategory category) {
        return repo.save(category);
    }

    public List<TicketCategory> getAll() {
        return repo.findAll();
    }
}

TicketCategoryController
package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.model.TicketCategory;
import com.example.demo.service.TicketCategoryService;

@RestController
@RequestMapping("/api/categories")
public class TicketCategoryController {

    private final TicketCategoryService service;

    public TicketCategoryController(TicketCategoryService service) {
        this.service = service;
    }

    @PostMapping
    public TicketCategory save(@RequestBody TicketCategory c) {
        return service.save(c);
    }

    @GetMapping
    public List<TicketCategory> getAll() {
        return service.getAll();
    }
}

✅ 3. TICKET
TicketRepository
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.demo.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUserId(Long userId);
}

TicketService
package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Ticket;

public interface TicketService {
    Ticket save(Ticket ticket);
    List<Ticket> getAll();
}

TicketServiceImpl
package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.model.Ticket;
import com.example.demo.repository.TicketRepository;
import com.example.demo.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository repo;

    public TicketServiceImpl(TicketRepository repo) {
        this.repo = repo;
    }

    public Ticket save(Ticket ticket) {
        return repo.save(ticket);
    }

    public List<Ticket> getAll() {
        return repo.findAll();
    }
}

TicketController
package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.model.Ticket;
import com.example.demo.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping
    public Ticket save(@RequestBody Ticket ticket) {
        return service.save(ticket);
    }

    @GetMapping
    public List<Ticket> getAll() {
        return service.getAll();
    }
}

✅ 4. DUPLICATE RULE
DuplicateRuleRepository
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.DuplicateRule;

public interface DuplicateRuleRepository extends JpaRepository<DuplicateRule, Long> {
}

DuplicateRuleService
package com.example.demo.service;

import java.util.List;
import com.example.demo.model.DuplicateRule;

public interface DuplicateRuleService {
    DuplicateRule save(DuplicateRule rule);
    List<DuplicateRule> getAll();
}

DuplicateRuleServiceImpl
package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.model.DuplicateRule;
import com.example.demo.repository.DuplicateRuleRepository;
import com.example.demo.service.DuplicateRuleService;

@Service
public class DuplicateRuleServiceImpl implements DuplicateRuleService {

    private final DuplicateRuleRepository repo;

    public DuplicateRuleServiceImpl(DuplicateRuleRepository repo) {
        this.repo = repo;
    }

    public DuplicateRule save(DuplicateRule rule) {
        return repo.save(rule);
    }

    public List<DuplicateRule> getAll() {
        return repo.findAll();
    }
}

DuplicateRuleController
package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.model.DuplicateRule;
import com.example.demo.service.DuplicateRuleService;

@RestController
@RequestMapping("/api/rules")
public class DuplicateRuleController {

    private final DuplicateRuleService service;

    public DuplicateRuleController(DuplicateRuleService service) {
        this.service = service;
    }

    @PostMapping
    public DuplicateRule save(@RequestBody DuplicateRule rule) {
        return service.save(rule);
    }

    @GetMapping
    public List<DuplicateRule> getAll() {
        return service.getAll();
    }
}

✅ 5. DUPLICATE DETECTION LOG
DuplicateDetectionLogRepository
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.demo.model.DuplicateDetectionLog;

public interface DuplicateDetectionLogRepository extends JpaRepository<DuplicateDetectionLog, Long> {
    List<DuplicateDetectionLog> findByTicketId(Long ticketId);
}

DuplicateDetectionLogService
package com.example.demo.service;

import java.util.List;
import com.example.demo.model.DuplicateDetectionLog;

public interface DuplicateDetectionLogService {
    List<DuplicateDetectionLog> getByTicketId(Long ticketId);
}

DuplicateDetectionLogServiceImpl
package com.example.demo.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.demo.model.DuplicateDetectionLog;
import com.example.demo.repository.DuplicateDetectionLogRepository;
import com.example.demo.service.DuplicateDetectionLogService;

@Service
public class DuplicateDetectionLogServiceImpl implements DuplicateDetectionLogService {

    private final DuplicateDetectionLogRepository repo;

    public DuplicateDetectionLogServiceImpl(DuplicateDetectionLogRepository repo) {
        this.repo = repo;
    }

    public List<DuplicateDetectionLog> getByTicketId(Long ticketId) {
        return repo.findByTicketId(ticketId);
    }
}

DuplicateDetectionLogController
package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.model.DuplicateDetectionLog;
import com.example.demo.service.DuplicateDetectionLogService;

@RestController
@RequestMapping("/api/detection")
public class DuplicateDetectionLogController {

    private final DuplicateDetectionLogService service;

    public DuplicateDetectionLogController(DuplicateDetectionLogService service) {
        this.service = service;
    }

    @GetMapping("/ticket/{ticketId}")
    public List<DuplicateDetectionLog> getByTicket(@PathVariable Long ticketId) {
        return service.getByTicketId(ticketId);
    }
}

✅ FINAL STATUS

✔ Each entity has its own Repository, Service, ServiceImpl, Controller
✔ No relationships
✔ No validation
✔ No exceptions
✔ Constructor injection only
✔ Portal-safe

If you want, I can now:

🔹 Align method names exactly to your portal test cases

🔹 Reduce further (only save + getAll)

🔹 Convert to Student-style CRUD like your original example

model 

Below are ONLY the MODEL (Entity) classes — nothing else — exactly as you asked.

✔ No relationships
✔ No validation
✔ Default constructor
✔ Getters & setters
✔ Root package com.example.demo.model

1️⃣ User.java
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getMatchedTicketId() {
        return matchedTicketId;
    }

    public void setMatchedTicketId(Long matchedTicketId) {
        this.matchedTicketId = matchedTicketId;
    }

    public Double getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(Double matchScore) {
        this.matchScore = matchScore;
    }
}


If