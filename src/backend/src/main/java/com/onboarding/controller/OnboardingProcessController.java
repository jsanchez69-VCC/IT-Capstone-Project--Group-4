package com.onboarding.controller;

import com.onboarding.dto.StartProcessRequest;
import com.onboarding.model.ChecklistTask;
import com.onboarding.model.OnboardingProcess;
import com.onboarding.model.TaskStatus;
import com.onboarding.service.OnboardingProcessService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/processes")
public class OnboardingProcessController {

    private final OnboardingProcessService processService;

    public OnboardingProcessController(OnboardingProcessService processService) {
        this.processService = processService;
    }

    // Starts an onboarding or offboarding process for an employee
    @PostMapping("/start")
    public ResponseEntity<OnboardingProcess> startProcess(@Valid @RequestBody StartProcessRequest request) {
        return ResponseEntity.ok(processService.startProcess(request));
    }

    @GetMapping
    public ResponseEntity<List<OnboardingProcess>> getAllProcesses() {
        return ResponseEntity.ok(processService.getAllProcesses());
    }

    // View current status of a process (NOT_STARTED / IN_PROGRESS / COMPLETED) plus its tasks
    @GetMapping("/{id}")
    public ResponseEntity<OnboardingProcess> getProcess(@PathVariable Long id) {
        return ResponseEntity.ok(processService.getProcessById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<OnboardingProcess>> getProcessesForEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(processService.getProcessesForEmployee(employeeId));
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<ChecklistTask>> getTasks(@PathVariable Long id) {
        return ResponseEntity.ok(processService.getTasksForProcess(id));
    }

    // Update a checklist task's status, e.g. { "status": "DONE" }
    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<ChecklistTask> updateTaskStatus(@PathVariable Long taskId,
                                                           @RequestBody Map<String, String> body) {
        TaskStatus status = TaskStatus.valueOf(body.get("status").toUpperCase());
        return ResponseEntity.ok(processService.updateTaskStatus(taskId, status));
    }
}
