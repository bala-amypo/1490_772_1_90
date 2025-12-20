package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.TicketCategoryModel;
import com.example.demo.model.TicketModel;
import com.example.demo.model.UserModel;
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
            throw new IllegalArgumentException("Subject cannot be empty");
        }

        if (ticket.getDescription() == null || ticket.getDescription().length() < 10) {
            throw new IllegalArgumentException("Description must be at least 10 characters");
        }

        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        TicketCategoryModel category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        ticket.setUser(user);
        ticket.setCategory(category);

        return ticketRepository.save(ticket);
    }

    @Override
    public TicketModel getTicket(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + ticketId));
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
