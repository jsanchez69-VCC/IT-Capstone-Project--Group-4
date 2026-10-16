package com.onboarding.service;

import com.onboarding.dto.IssueEquipmentRequest;
import com.onboarding.model.Employee;
import com.onboarding.model.Equipment;
import com.onboarding.model.EquipmentStatus;
import com.onboarding.repository.EmployeeRepository;
import com.onboarding.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EmployeeRepository employeeRepository;

    public EquipmentService(EquipmentRepository equipmentRepository, EmployeeRepository employeeRepository) {
        this.equipmentRepository = equipmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public Equipment addEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    public List<Equipment> getAvailableEquipment() {
        return equipmentRepository.findByStatus(EquipmentStatus.AVAILABLE);
    }

    public List<Equipment> getEquipmentForEmployee(Long employeeId) {
        return equipmentRepository.findByIssuedToId(employeeId);
    }

    // Issues a piece of equipment to an employee (used during onboarding)
    public Equipment issueEquipment(IssueEquipmentRequest request) {
        Equipment equipment = equipmentRepository.findById(request.getEquipmentId())
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found: " + request.getEquipmentId()));
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + request.getEmployeeId()));

        if (equipment.getStatus() != EquipmentStatus.AVAILABLE) {
            throw new IllegalStateException("Equipment is not available to issue");
        }

        equipment.setIssuedTo(employee);
        equipment.setStatus(EquipmentStatus.ISSUED);
        equipment.setIssuedDate(LocalDate.now());
        equipment.setReturnedDate(null);
        equipment.setReceiptSigned(false);

        return equipmentRepository.save(equipment);
    }

    // Marks equipment as returned/checked-in (used during offboarding)
    public Equipment returnEquipment(Long equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found: " + equipmentId));

        equipment.setStatus(EquipmentStatus.RETURNED);
        equipment.setReturnedDate(LocalDate.now());

        return equipmentRepository.save(equipment);
    }

    // Confirms the employee has signed the issue/return receipt
    public Equipment signReceipt(Long equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found: " + equipmentId));
        equipment.setReceiptSigned(true);
        return equipmentRepository.save(equipment);
    }
}
