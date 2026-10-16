package com.onboarding.service;

import com.onboarding.model.EquipmentStatus;
import com.onboarding.model.ProcessStatus;
import com.onboarding.model.ProcessType;
import com.onboarding.model.TicketStatus;
import com.onboarding.repository.EmployeeRepository;
import com.onboarding.repository.EquipmentRepository;
import com.onboarding.repository.OnboardingProcessRepository;
import com.onboarding.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final EmployeeRepository employeeRepository;
    private final OnboardingProcessRepository processRepository;
    private final EquipmentRepository equipmentRepository;
    private final TicketRepository ticketRepository;

    public DashboardService(EmployeeRepository employeeRepository,
                            OnboardingProcessRepository processRepository,
                            EquipmentRepository equipmentRepository,
                            TicketRepository ticketRepository) {
        this.employeeRepository = employeeRepository;
        this.processRepository = processRepository;
        this.equipmentRepository = equipmentRepository;
        this.ticketRepository = ticketRepository;
    }

    // One call gives the frontend every number the dashboard cards need
    public Map<String, Object> getSummary() {
        Map<String, Object> summary = new LinkedHashMap<>();

        summary.put("totalEmployees", employeeRepository.count());

        Map<String, Object> processes = new LinkedHashMap<>();
        // "Active" = not yet COMPLETED
        processes.put("activeOnboardings",
                processRepository.countByTypeAndStatusNot(ProcessType.ONBOARDING, ProcessStatus.COMPLETED));
        processes.put("activeOffboardings",
                processRepository.countByTypeAndStatusNot(ProcessType.OFFBOARDING, ProcessStatus.COMPLETED));
        processes.put("notStarted", processRepository.countByStatus(ProcessStatus.NOT_STARTED));
        processes.put("inProgress", processRepository.countByStatus(ProcessStatus.IN_PROGRESS));
        processes.put("completed", processRepository.countByStatus(ProcessStatus.COMPLETED));
        summary.put("processes", processes);

        Map<String, Object> equipment = new LinkedHashMap<>();
        equipment.put("total", equipmentRepository.count());
        equipment.put("available", equipmentRepository.countByStatus(EquipmentStatus.AVAILABLE));
        equipment.put("issued", equipmentRepository.countByStatus(EquipmentStatus.ISSUED));
        equipment.put("returned", equipmentRepository.countByStatus(EquipmentStatus.RETURNED));
        equipment.put("lostOrDamaged", equipmentRepository.countByStatus(EquipmentStatus.LOST_OR_DAMAGED));
        summary.put("equipment", equipment);

        Map<String, Object> tickets = new LinkedHashMap<>();
        tickets.put("total", ticketRepository.count());
        tickets.put("open", ticketRepository.countByStatus(TicketStatus.OPEN));
        tickets.put("inProgress", ticketRepository.countByStatus(TicketStatus.IN_PROGRESS));
        tickets.put("resolved", ticketRepository.countByStatus(TicketStatus.RESOLVED));
        tickets.put("closed", ticketRepository.countByStatus(TicketStatus.CLOSED));
        summary.put("tickets", tickets);

        return summary;
    }
}
