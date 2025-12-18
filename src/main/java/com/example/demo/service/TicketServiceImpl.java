package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.TicketCategoryModel;
import com.example.demo.entity.TicketModel;
import com.example.demo.entity.UserModel;
import com.example.demo.repository.TicketCategoryRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketCategoryRepository categoryRepository;

    public TicketServiceImpl(
            TicketRepository ticketRepository,
            UserRepository userRepository,
            TicketCategoryRepository categoryRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public TicketModel createTicket(Long userId, Long categoryId, TicketModel ticket) {

        if (ticket.getSubject() == null || ticket.getSubject().trim().isEmpty()) {
            throw new RuntimeException("Subject cannot be empty");
        }

        if (ticket.getDescription() == null || ticket.getDescription().length() < 10) {
            throw new RuntimeException("Description is too short");
        }

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TicketCategoryModel category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ticket.setUser(user);
        ticket.setCategory(category);

        return ticketRepository.save(ticket);
    }

    @Override
    public TicketModel getTicket(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    @Override
    public List<TicketModel> getTicketsByUser(Long userId) {
        return ticketRepository.findByUser_Id(userId);
    }

    @Override
    public List<TicketModel> getAllTickets() {
        return ticketRepository.findAll();
    }
}
