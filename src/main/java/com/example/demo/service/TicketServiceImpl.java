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