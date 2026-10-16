package com.onboarding.service;

import com.onboarding.dto.StartProcessRequest;
import com.onboarding.model.*;
import com.onboarding.repository.ChecklistTaskRepository;
import com.onboarding.repository.EmployeeRepository;
import com.onboarding.repository.OnboardingProcessRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OnboardingProcessService {

    private final OnboardingProcessRepository processRepository;
    private final ChecklistTaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    public OnboardingProcessService(OnboardingProcessRepository processRepository,
                                     ChecklistTaskRepository taskRepository,
                                     EmployeeRepository employeeRepository) {
        this.processRepository = processRepository;
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
    }

    // Default checklist templates used when the caller doesn't supply custom tasks
    private static final List<String> DEFAULT_ONBOARDING_TASKS = List.of(
            "Create employee email account",
            "Set up system/network login credentials",
            "Assign required equipment",
            "Grant building/access card permissions",
            "Complete new-hire paperwork"
    );

    private static final List<String> DEFAULT_OFFBOARDING_TASKS = List.of(
            "Revoke system/network login credentials",
            "Collect and check in all issued equipment",
            "Revoke building/access card permissions",
            "Disable email account",
            "Conduct exit interview / final paperwork"
    );

    public OnboardingProcess startProcess(StartProcessRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + request.getEmployeeId()));

        LocalDate startDate = request.getStartDate() != null ? request.getStartDate() : LocalDate.now();
        if (request.getEndDate() != null && request.getEndDate().isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        OnboardingProcess process = new OnboardingProcess(employee, request.getType(), startDate);
        process.setEndDate(request.getEndDate());
        process = processRepository.save(process);

        List<String> descriptions = (request.getTaskDescriptions() != null && !request.getTaskDescriptions().isEmpty())
                ? request.getTaskDescriptions()
                : (request.getType() == ProcessType.ONBOARDING ? DEFAULT_ONBOARDING_TASKS : DEFAULT_OFFBOARDING_TASKS);

        for (String description : descriptions) {
            ChecklistTask saved = taskRepository.save(new ChecklistTask(process, description, null));
            process.getTasks().add(saved); // so the response includes the checklist
        }

        process.recalculateStatus();
        return processRepository.save(process);
    }

    public List<OnboardingProcess> getAllProcesses() {
        return processRepository.findAll();
    }

    public OnboardingProcess getProcessById(Long id) {
        return processRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Process not found: " + id));
    }

    public List<OnboardingProcess> getProcessesForEmployee(Long employeeId) {
        return processRepository.findByEmployeeId(employeeId);
    }

    // Updates a single checklist task's status, then recalculates overall process status
    public ChecklistTask updateTaskStatus(Long taskId, TaskStatus newStatus) {
        ChecklistTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));
        task.setStatus(newStatus);
        taskRepository.save(task);

        OnboardingProcess process = task.getProcess();
        process.recalculateStatus();
        processRepository.save(process);

        return task;
    }

    public List<ChecklistTask> getTasksForProcess(Long processId) {
        return taskRepository.findByProcessId(processId);
    }
}
