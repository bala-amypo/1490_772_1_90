package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Ticket;

public interface TicketService {

    Ticket createTicket(Ticket ticket);  // simplified

    Ticket getTicket(Long ticketId);

    List<Ticket> getTicketsByUser(Long userId);

    List<Ticket> getAllTickets();
}
