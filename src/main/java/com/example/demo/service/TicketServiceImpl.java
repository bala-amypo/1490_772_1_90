package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
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

    public TicketServiceImpl(TicketRepository ticketRepo, UserRepository userRepo, TicketCategoryRepository catRepo) {
        this.ticketRepo = ticketRepo;
        this.userRepo = userRepo;
        this.catRepo = catRepo;
    }

    @Override
    public Ticket createTicket(Long userId, Long categoryId, Ticket ticket) {
        User user = userRepo.findById(userId).orElse(null);
        TicketCategory category = catRepo.findById(categoryId).orElse(null);

        if (user == null || category == null || ticket.getDescription().length() < 10)
            return null;

        ticket.setUser(user);
        ticket.setCategory(category);
        return ticketRepo.save(ticket);
    }

    @Override
    public Ticket getTicket(Long ticketId) {
        return ticketRepo.findById(ticketId).orElse(null);
    }

    @Override
    public List<Ticket> getTicketsByUser(Long userId) {
        if (!userRepo.existsById(userId)) return List.of();
        return ticketRepo.findByUser_Id(userId);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepo.findAll();
    }
}
