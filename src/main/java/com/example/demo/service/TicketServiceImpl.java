package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Ticket;
import com.example.demo.model.User;
import com.example.demo.model.TicketCategory;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.TicketCategoryRepository;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepo;
    private final UserRepository userRepo;
    private final TicketCategoryRepository catRepo;

    // CORRECT CONSTRUCTOR SIGNATURE
    public TicketServiceImpl(TicketRepository ticketRepo, UserRepository userRepo, TicketCategoryRepository catRepo) {
        this.ticketRepo = ticketRepo;
        this.userRepo = userRepo;
        this.catRepo = catRepo;
    }

    @Override
    public Ticket createTicket(Long userId, Long categoryId, Ticket ticket) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        TicketCategory category = catRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category not found"));

        // Validation: description length
        if (ticket.getDescription().length() < 10)
            throw new IllegalArgumentException("description too short");

        ticket.setUser(user);
        ticket.setCategory(category);
        return ticketRepo.save(ticket);
    }

    @Override
    public Ticket getTicket(Long ticketId) {
        return ticketRepo.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("ticket not found"));
    }

    @Override
    public List<Ticket> getTicketsByUser(Long userId) {
        if (!userRepo.existsById(userId))
            throw new ResourceNotFoundException("user not found");
        return ticketRepo.findByUser__ld(userId); // Use double underscore method
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepo.findAll();
    }
}