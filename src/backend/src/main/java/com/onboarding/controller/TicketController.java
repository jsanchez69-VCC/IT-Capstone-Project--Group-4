package com.onboarding.controller;

import com.onboarding.dto.CreateTicketRequest;
import com.onboarding.dto.UpdateTicketRequest;
import com.onboarding.model.Ticket;
import com.onboarding.model.TicketStatus;
import com.onboarding.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // Submit a new help desk ticket
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@Valid @RequestBody CreateTicketRequest request) {
        return ResponseEntity.ok(ticketService.createTicket(request));
    }

    // List all tickets, or filter with ?status=OPEN
    @GetMapping
    public ResponseEntity<List<Ticket>> getTickets(@RequestParam(required = false) TicketStatus status) {
        return ResponseEntity.ok(ticketService.getTickets(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Ticket>> getTicketsForEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(ticketService.getTicketsForEmployee(employeeId));
    }

    // Change status / priority / assignee, e.g. { "status": "RESOLVED", "assignedTo": "Paul" }
    @PatchMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id,
                                               @RequestBody UpdateTicketRequest request) {
        return ResponseEntity.ok(ticketService.updateTicket(id, request));
    }
}
