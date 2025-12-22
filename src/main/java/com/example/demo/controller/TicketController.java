package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.model.Ticket;
import com.example.demo.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping
    public Ticket save(@RequestBody Ticket ticket) {
        return service.save(ticket);
    }

    @GetMapping
    public List<Ticket> getAll() {
        return service.getAll();
    }
}