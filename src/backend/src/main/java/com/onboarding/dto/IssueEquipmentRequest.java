package com.onboarding.dto;

import jakarta.validation.constraints.NotNull;

public class IssueEquipmentRequest {
    @NotNull
    private Long equipmentId;

    @NotNull
    private Long employeeId;

    public Long getEquipmentId() { return equipmentId; }
    public void setEquipmentId(Long equipmentId) { this.equipmentId = equipmentId; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
}
