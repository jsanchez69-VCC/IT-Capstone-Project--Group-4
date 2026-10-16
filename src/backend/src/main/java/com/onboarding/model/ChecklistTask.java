package com.onboarding.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
 import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "checklist_tasks")
public class ChecklistTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "process_id", nullable = false)
    private OnboardingProcess process;

    @NotBlank
    @Column(nullable = false)
    private String description; // e.g. "Create email account", "Revoke building access"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.PENDING;

    private String assignedTo; // team member name/role, e.g. "IT/Security"

    public ChecklistTask() {}

    public ChecklistTask(OnboardingProcess process, String description, String assignedTo) {
        this.process = process;
        this.description = description;
        this.assignedTo = assignedTo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public OnboardingProcess getProcess() { return process; }
    public void setProcess(OnboardingProcess process) { this.process = process; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }

    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
}
