package com.onboarding.repository;

import com.onboarding.model.OnboardingProcess;
import com.onboarding.model.ProcessStatus;
import com.onboarding.model.ProcessType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnboardingProcessRepository extends JpaRepository<OnboardingProcess, Long> {
    List<OnboardingProcess> findByEmployeeId(Long employeeId);
    long countByTypeAndStatusNot(ProcessType type, ProcessStatus status);
    long countByStatus(ProcessStatus status);
}
