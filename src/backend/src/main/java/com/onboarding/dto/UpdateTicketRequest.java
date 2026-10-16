package com.onboarding.dto;

import com.onboarding.model.TicketPriority;
import com.onboarding.model.TicketStatus;

// Every field is optional - only the fields you send are changed
public class UpdateTicketRequest {
    private TicketStatus status;
    private TicketPriority priority;
    private String assignedTo;

    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }

    public TicketPriority getPriority() { return priority; }
    public void setPriority(TicketPriority priority) { this.priority = priority; }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
}
