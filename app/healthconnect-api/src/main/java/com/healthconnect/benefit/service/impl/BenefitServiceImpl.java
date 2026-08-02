package com.healthconnect.benefit.service.impl;

import com.healthconnect.audit.service.AuditService;
import com.healthconnect.benefit.dto.request.CreateBenefitRequest;
import com.healthconnect.benefit.dto.response.BenefitResponse;
import com.healthconnect.benefit.entity.Benefit;
import com.healthconnect.benefit.mapper.BenefitMapper;
import com.healthconnect.benefit.repository.BenefitRepository;
import com.healthconnect.benefit.service.BenefitService;
import com.healthconnect.enrollment.entity.Enrollment;
import com.healthconnect.enrollment.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BenefitServiceImpl implements BenefitService {

    private final BenefitRepository benefitRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final BenefitMapper benefitMapper;
    private final AuditService auditService;

    @Override
    public BenefitResponse createBenefit(CreateBenefitRequest request) {

        if (benefitRepository.existsByBenefitCode(request.getBenefitCode())) {
            throw new RuntimeException("Benefit code already exists.");
        }

        Enrollment enrollment = enrollmentRepository.findById(request.getEnrollmentId())
                .orElseThrow(() -> new RuntimeException("Enrollment not found."));

        Benefit benefit = benefitMapper.toEntity(request);

        benefit.setEnrollment(enrollment);

        Benefit savedBenefit = benefitRepository.save(benefit);
        auditService.logEvent(
        "BENEFIT_CREATED",
        "Benefit",
        savedBenefit.getId(),
        "Benefit " + savedBenefit.getBenefitName() + " created.",
        "SYSTEM");

        return benefitMapper.toResponse(savedBenefit);
    }
}