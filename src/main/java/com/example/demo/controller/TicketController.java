package com.example.demo.controller;

import com.example.demo.model.Ticket;
import com.example.demo.service.TicketService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;
    
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    
    @PostMapping("/{userId}/{categoryId}")
    public Ticket createTicket(@PathVariable Long userId, 
                              @PathVariable Long categoryId,
                              @RequestBody Ticket ticket) {
        return ticketService.createTicket(userId, categoryId, ticket);
    }
    
    @GetMapping("/user/{userId}")
    public List<Ticket> getTicketsByUser(@PathVariable Long userId) {
        return ticketService.getTicketsByUser(userId);
    }
    
    @GetMapping("/all")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }
    
    @GetMapping("/{id}")
    public Ticket getTicket(@PathVariable Long id) {
        return ticketService.getTicket(id);
    }
}