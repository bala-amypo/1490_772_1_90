package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.TicketModel;

public interface TicketService {

    TicketModel createTicket(Long userId, Long categoryId, TicketModel ticket);

    TicketModel getTicket(Long ticketId);

    List<TicketModel> getTicketsByUser(Long userId);

    List<TicketModel> getAllTickets();
}
