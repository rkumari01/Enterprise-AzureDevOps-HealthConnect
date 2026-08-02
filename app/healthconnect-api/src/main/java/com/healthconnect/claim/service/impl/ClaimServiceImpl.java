package com.healthconnect.claim.service.impl;

import com.healthconnect.audit.service.AuditService;
import com.healthconnect.claim.dto.request.CreateClaimRequest;
import com.healthconnect.claim.dto.response.ClaimResponse;
import com.healthconnect.claim.entity.Claim;
import com.healthconnect.claim.mapper.ClaimMapper;
import com.healthconnect.claim.repository.ClaimRepository;
import com.healthconnect.claim.service.ClaimService;
import com.healthconnect.enrollment.entity.Enrollment;
import com.healthconnect.enrollment.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ClaimMapper claimMapper;
    private final AuditService auditService;

    @Override
    public ClaimResponse createClaim(CreateClaimRequest request) {

        if (claimRepository.existsByClaimNumber(request.getClaimNumber())) {
            throw new RuntimeException("Claim number already exists.");
        }

        Enrollment enrollment = enrollmentRepository.findById(request.getEnrollmentId())
                .orElseThrow(() -> new RuntimeException("Enrollment not found."));

        Claim claim = claimMapper.toEntity(request);

        claim.setEnrollment(enrollment);

        Claim savedClaim = claimRepository.save(claim);
        auditService.logEvent(
        "CLAIM_SUBMITTED",
        "Claim",
        savedClaim.getId(),
        "Claim " + savedClaim.getClaimNumber() + " submitted.",
        "SYSTEM");

        return claimMapper.toResponse(savedClaim);
    }
}