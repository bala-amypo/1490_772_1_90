package com.example.demo.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Ticket;
import com.example.demo.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping("/{userId}/{categoryId}")
    public Ticket create(@PathVariable Long userId, @PathVariable Long categoryId, @RequestBody Ticket ticket) {
        // Set simplified IDs instead of full objects
        ticket.setUserId(userId);
        ticket.setCategoryId(categoryId);
        return service.createTicket(ticket);
    }

    @GetMapping("/{id}")
    public Ticket get(@PathVariable Long id) {
        return service.getTicket(id);
    }

    @GetMapping("/user/{userId}")
    public List<Ticket> getByUser(@PathVariable Long userId) {
        return service.getTicketsByUser(userId);
    }

    @GetMapping("/all")
    public List<Ticket> getAll() {
        return service.getAllTickets();
    }
}
