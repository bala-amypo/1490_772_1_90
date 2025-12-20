package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.TicketModel;
import com.example.demo.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // CREATE TICKET
    @PostMapping("/{userId}/{categoryId}")
    public TicketModel createTicket(
            @PathVariable Long userId,
            @PathVariable Long categoryId,
            @RequestBody TicketModel ticket) {
        return ticketService.createTicket(userId, categoryId, ticket);
    }

    // GET TICKETS BY USER
    @GetMapping("/user/{userId}")
    public List<TicketModel> getTicketsByUser(@PathVariable Long userId) {
        return ticketService.getTicketsByUser(userId);
    }

    // GET ALL TICKETS
    @GetMapping("/all")
    public List<TicketModel> getAllTickets() {
        return ticketService.getAllTickets();
    }

    // GET TICKET BY ID
    @GetMapping("/{id}")
    public TicketModel getTicket(@PathVariable Long id) {
        return ticketService.getTicket(id);
    }
}
