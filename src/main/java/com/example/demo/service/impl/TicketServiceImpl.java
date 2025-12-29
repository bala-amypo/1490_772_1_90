package com.example.demo.service.impl;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Ticket;
import com.example.demo.model.TicketCategory;
import com.example.demo.model.User;
import com.example.demo.repository.TicketCategoryRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TicketService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketCategoryRepository categoryRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, UserRepository userRepository, TicketCategoryRepository categoryRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Ticket createTicket(Long userId, Long categoryId, Ticket ticket) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found"));
        TicketCategory category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new NotFoundException("Category not found"));
        
        if (ticket.getSubject() == null || ticket.getSubject().isBlank()) {
            throw new IllegalArgumentException("Subject is required");
        }
        if (ticket.getDescription() == null || ticket.getDescription().length() < 10) {
            throw new IllegalArgumentException("Description must be at least 10 characters");
        }
        
        ticket.setUser(user);
        ticket.setCategory(category);
        if (ticket.getStatus() == null) {
            ticket.setStatus("OPEN");
        }
        if (ticket.getCreatedAt() == null) {
            ticket.setCreatedAt(LocalDateTime.now());
        }
        
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket getTicket(Long id) {
        return ticketRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Ticket not found"));
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public List<Ticket> getTicketsByUser(Long userId) {
        return ticketRepository.findByUser_Id(userId);
    }
}