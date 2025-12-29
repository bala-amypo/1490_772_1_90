package com.example.demo.controller;

import com.example.demo.model.Ticket;
import com.example.demo.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Tickets", description = "Ticket management endpoints")
public class TicketController {
    
    private final TicketService ticketService;
    
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    
    @PostMapping("/{userId}/{categoryId}")
    @Operation(summary = "Create a new ticket")
    public ResponseEntity<Ticket> createTicket(@PathVariable Long userId, 
                                             @PathVariable Long categoryId,
                                             @RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketService.createTicket(userId, categoryId, ticket));
    }
    
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get tickets by user")
    public ResponseEntity<List<Ticket>> getTicketsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ticketService.getTicketsByUser(userId));
    }
    
    @GetMapping("/all")
    @Operation(summary = "Get all tickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get ticket by ID")
    public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicket(id));
    }
}