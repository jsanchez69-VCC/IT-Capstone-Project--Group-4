package com.onboarding.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "onboarding_processes")
public class OnboardingProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProcessType type; // ONBOARDING or OFFBOARDING

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProcessStatus status = ProcessStatus.NOT_STARTED;

    @Column(nullable = false)
    private LocalDate startDate;

    // Optional planned end date (e.g. last day of work for offboarding)
    private LocalDate endDate;

    private LocalDate completedDate;

    @OneToMany(mappedBy = "process", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChecklistTask> tasks = new ArrayList<>();

    public OnboardingProcess() {}

    public OnboardingProcess(Employee employee, ProcessType type, LocalDate startDate) {
        this.employee = employee;
        this.type = type;
        this.startDate = startDate;
    }

    // Recomputes overall status from the underlying tasks - call after any task update
    public void recalculateStatus() {
        if (tasks.isEmpty()) {
            this.status = ProcessStatus.NOT_STARTED;
            return;
        }
        boolean allDone = tasks.stream().allMatch(t -> t.getStatus() == TaskStatus.DONE);
        boolean anyStarted = tasks.stream().anyMatch(t -> t.getStatus() != TaskStatus.PENDING);

        if (allDone) {
            this.status = ProcessStatus.COMPLETED;
            this.completedDate = LocalDate.now();
        } else if (anyStarted) {
            this.status = ProcessStatus.IN_PROGRESS;
        } else {
            this.status = ProcessStatus.NOT_STARTED;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public ProcessType getType() { return type; }
    public void setType(ProcessType type) { this.type = type; }

    public ProcessStatus getStatus() { return status; }
    public void setStatus(ProcessStatus status) { this.status = status; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public LocalDate getCompletedDate() { return completedDate; }
    public void setCompletedDate(LocalDate completedDate) { this.completedDate = completedDate; }

    public List<ChecklistTask> getTasks() { return tasks; }
    public void setTasks(List<ChecklistTask> tasks) { this.tasks = tasks; }
}
