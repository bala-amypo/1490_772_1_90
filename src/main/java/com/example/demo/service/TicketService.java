package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Ticket;

public interface TicketService {
    Ticket save(Ticket ticket);
    List<Ticket> getAll();
}