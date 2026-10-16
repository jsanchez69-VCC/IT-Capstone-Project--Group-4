package com.onboarding.repository;

import com.onboarding.model.Equipment;
import com.onboarding.model.EquipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByStatus(EquipmentStatus status);
    List<Equipment> findByIssuedToId(Long employeeId);
    long countByStatus(EquipmentStatus status);
}
