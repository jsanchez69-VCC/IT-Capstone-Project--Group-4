package com.onboarding.repository;

import com.onboarding.model.ChecklistTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistTaskRepository extends JpaRepository<ChecklistTask, Long> {
    List<ChecklistTask> findByProcessId(Long processId);
}
