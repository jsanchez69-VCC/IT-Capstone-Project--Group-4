package com.onboarding.repository;

import com.onboarding.model.Ticket;
import com.onboarding.model.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByCreatedById(Long employeeId);
    long countByStatus(TicketStatus status);
}
