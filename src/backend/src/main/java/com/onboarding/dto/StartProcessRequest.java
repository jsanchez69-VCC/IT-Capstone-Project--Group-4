package com.onboarding.dto;

import com.onboarding.model.ProcessType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class StartProcessRequest {
    @NotNull
    private Long employeeId;

    @NotNull
    private ProcessType type;

    // Optional dates (format yyyy-MM-dd). If startDate is omitted, today is used.
    private LocalDate startDate;
    private LocalDate endDate;

    // Optional starter checklist; if omitted, a default template is used
    private List<String> taskDescriptions;

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public ProcessType getType() { return type; }
    public void setType(ProcessType type) { this.type = type; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public List<String> getTaskDescriptions() { return taskDescriptions; }
    public void setTaskDescriptions(List<String> taskDescriptions) { this.taskDescriptions = taskDescriptions; }
}
