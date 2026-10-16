package com.onboarding.controller;

import com.onboarding.dto.IssueEquipmentRequest;
import com.onboarding.model.Equipment;
import com.onboarding.service.EquipmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PostMapping
    public ResponseEntity<Equipment> addEquipment(@Valid @RequestBody Equipment equipment) {
        return ResponseEntity.ok(equipmentService.addEquipment(equipment));
    }

    @GetMapping
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        return ResponseEntity.ok(equipmentService.getAllEquipment());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Equipment>> getAvailableEquipment() {
        return ResponseEntity.ok(equipmentService.getAvailableEquipment());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Equipment>> getEquipmentForEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(equipmentService.getEquipmentForEmployee(employeeId));
    }

    @PostMapping("/issue")
    public ResponseEntity<Equipment> issueEquipment(@Valid @RequestBody IssueEquipmentRequest request) {
        return ResponseEntity.ok(equipmentService.issueEquipment(request));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Equipment> returnEquipment(@PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.returnEquipment(id));
    }

    @PostMapping("/{id}/sign-receipt")
    public ResponseEntity<Equipment> signReceipt(@PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.signReceipt(id));
    }
}
