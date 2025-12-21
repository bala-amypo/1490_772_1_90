package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.model.Ticket;
import com.example.demo.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepo;

    public TicketServiceImpl(TicketRepository ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    @Override
    public Ticket createTicket(Ticket ticket) {
        // Optional validation
        if (ticket.getDescription() == null || ticket.getDescription().length() < 10) return null;

        return ticketRepo.save(ticket);
    }

    @Override
    public Ticket getTicket(Long ticketId) {
        return ticketRepo.findById(ticketId).orElse(null);
    }

    @Override
    public List<Ticket> getTicketsByUser(Long userId) {
        return ticketRepo.findByUserId(userId); // repository method
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepo.findAll();
    }
}
