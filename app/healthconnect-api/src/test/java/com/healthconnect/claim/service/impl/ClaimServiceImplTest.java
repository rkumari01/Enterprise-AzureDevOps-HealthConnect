package com.healthconnect.claim.service.impl;

import com.healthconnect.audit.service.AuditService;
import com.healthconnect.claim.dto.request.CreateClaimRequest;
import com.healthconnect.claim.dto.response.ClaimResponse;
import com.healthconnect.claim.entity.Claim;
import com.healthconnect.claim.mapper.ClaimMapper;
import com.healthconnect.claim.repository.ClaimRepository;
import com.healthconnect.enrollment.entity.Enrollment;
import com.healthconnect.enrollment.repository.EnrollmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClaimServiceImplTest {

    @Mock
    private ClaimRepository claimRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private ClaimMapper claimMapper;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private ClaimServiceImpl claimService;

    @Test
    void createClaim_ShouldCreateClaimSuccessfully() {

        CreateClaimRequest request = new CreateClaimRequest();
        request.setClaimNumber("CLM001");
        request.setClaimAmount(new BigDecimal("2500"));
        request.setEnrollmentId(1L);

        Enrollment enrollment = new Enrollment();

        Claim claim = new Claim();
        claim.setClaimNumber("CLM001");

        Claim savedClaim = new Claim();
        savedClaim.setId(1L);
        savedClaim.setClaimNumber("CLM001");

        ClaimResponse response = new ClaimResponse();
        response.setId(1L);
        response.setClaimNumber("CLM001");

        when(claimRepository.existsByClaimNumber("CLM001"))
                .thenReturn(false);

        when(enrollmentRepository.findById(1L))
                .thenReturn(Optional.of(enrollment));

        when(claimMapper.toEntity(request))
                .thenReturn(claim);

        when(claimRepository.save(claim))
                .thenReturn(savedClaim);

        when(claimMapper.toResponse(savedClaim))
                .thenReturn(response);

        ClaimResponse result = claimService.createClaim(request);

        assertNotNull(result);
        assertEquals("CLM001", result.getClaimNumber());

        verify(claimRepository).existsByClaimNumber("CLM001");
        verify(enrollmentRepository).findById(1L);
        verify(claimRepository).save(claim);

        verify(auditService).logEvent(
                eq("CLAIM_SUBMITTED"),
                eq("Claim"),
                eq(1L),
                contains("CLM001"),
                eq("SYSTEM"));
    }

    @Test
    void createClaim_ShouldThrowException_WhenClaimAlreadyExists() {

        CreateClaimRequest request = new CreateClaimRequest();
        request.setClaimNumber("CLM001");

        when(claimRepository.existsByClaimNumber("CLM001"))
                .thenReturn(true);

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> claimService.createClaim(request));

        assertEquals("Claim number already exists.", exception.getMessage());

        verify(claimRepository).existsByClaimNumber("CLM001");
        verify(claimRepository, never()).save(any());
    }

    @Test
    void createClaim_ShouldThrowException_WhenEnrollmentNotFound() {

        CreateClaimRequest request = new CreateClaimRequest();
        request.setClaimNumber("CLM001");
        request.setEnrollmentId(1L);

        when(claimRepository.existsByClaimNumber("CLM001"))
                .thenReturn(false);

        when(enrollmentRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> claimService.createClaim(request));

        assertEquals("Enrollment not found.", exception.getMessage());

        verify(enrollmentRepository).findById(1L);
        verify(claimRepository, never()).save(any());
    }
}