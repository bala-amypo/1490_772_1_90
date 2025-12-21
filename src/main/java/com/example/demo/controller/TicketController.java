package com.example.demo.controller;

import com.example.demo.model.Ticket;
import com.example.demo.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Ticket Management", description = "APIs for managing tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    @Operation(summary = "Create a new ticket")
    public ResponseEntity<Ticket> createTicket(
            @RequestParam Long userId,
            @RequestParam Long categoryId,
            @Valid @RequestBody Ticket ticket) {
        Ticket createdTicket = ticketService.createTicket(userId, categoryId, ticket);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ticket by ID")
    public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicket(id);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get tickets by user ID")
    public ResponseEntity<List<Ticket>> getTicketsByUser(@PathVariable Long userId) {
        List<Ticket> tickets = ticketService.getTicketsByUser(userId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping
    @Operation(summary = "Get all tickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }
}