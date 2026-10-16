package com.onboarding.service;

import com.onboarding.dto.CreateTicketRequest;
import com.onboarding.dto.UpdateTicketRequest;
import com.onboarding.model.*;
import com.onboarding.repository.EmployeeRepository;
import com.onboarding.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EmployeeRepository employeeRepository;

    public TicketService(TicketRepository ticketRepository, EmployeeRepository employeeRepository) {
        this.ticketRepository = ticketRepository;
        this.employeeRepository = employeeRepository;
    }

    public Ticket createTicket(CreateTicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        if (request.getPriority() != null) {
            ticket.setPriority(request.getPriority());
        }
        if (request.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + request.getEmployeeId()));
            ticket.setCreatedBy(employee);
        }
        return ticketRepository.save(ticket);
    }

    // If status is given, returns only tickets with that status
    public List<Ticket> getTickets(TicketStatus status) {
        return status == null ? ticketRepository.findAll() : ticketRepository.findByStatus(status);
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found: " + id));
    }

    public List<Ticket> getTicketsForEmployee(Long employeeId) {
        return ticketRepository.findByCreatedById(employeeId);
    }

    public Ticket updateTicket(Long id, UpdateTicketRequest request) {
        Ticket ticket = getTicketById(id);

        if (request.getPriority() != null) {
            ticket.setPriority(request.getPriority());
        }
        if (request.getAssignedTo() != null) {
            ticket.setAssignedTo(request.getAssignedTo());
        }
        if (request.getStatus() != null) {
            ticket.setStatus(request.getStatus());
            boolean finished = request.getStatus() == TicketStatus.RESOLVED
                    || request.getStatus() == TicketStatus.CLOSED;
            if (finished && ticket.getResolvedDate() == null) {
                ticket.setResolvedDate(LocalDateTime.now());
            } else if (!finished) {
                ticket.setResolvedDate(null); // reopened
            }
        }
        return ticketRepository.save(ticket);
    }
}
