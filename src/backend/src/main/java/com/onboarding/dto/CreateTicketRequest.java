package com.onboarding.dto;

import com.onboarding.model.TicketPriority;
import jakarta.validation.constraints.NotBlank;

public class CreateTicketRequest {
    @NotBlank
    private String title;

    private String description;
    private String category;

    // Optional; defaults to MEDIUM
    private TicketPriority priority;

    // Optional: the employee submitting the ticket
    private Long employeeId;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public TicketPriority getPriority() { return priority; }
    public void setPriority(TicketPriority priority) { this.priority = priority; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
}
