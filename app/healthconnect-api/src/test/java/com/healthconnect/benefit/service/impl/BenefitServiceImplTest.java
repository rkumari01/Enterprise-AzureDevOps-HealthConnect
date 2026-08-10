package com.healthconnect.benefit.service.impl;

import com.healthconnect.audit.service.AuditService;
import com.healthconnect.benefit.dto.request.CreateBenefitRequest;
import com.healthconnect.benefit.dto.response.BenefitResponse;
import com.healthconnect.benefit.entity.Benefit;
import com.healthconnect.benefit.mapper.BenefitMapper;
import com.healthconnect.benefit.repository.BenefitRepository;
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
class BenefitServiceImplTest {

    @Mock
    private BenefitRepository benefitRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private BenefitMapper benefitMapper;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private BenefitServiceImpl benefitService;

    @Test
    void createBenefit_ShouldCreateBenefitSuccessfully() {

        CreateBenefitRequest request = new CreateBenefitRequest();
        request.setBenefitCode("BEN001");
        request.setBenefitName("Medical");
        request.setDescription("Medical Coverage");
        request.setCoverageAmount(new BigDecimal("50000"));
        request.setIsActive(true);
        request.setEnrollmentId(1L);

        Enrollment enrollment = new Enrollment();

        Benefit benefit = new Benefit();
        benefit.setBenefitCode("BEN001");
        benefit.setBenefitName("Medical");

        Benefit savedBenefit = new Benefit();
        savedBenefit.setId(1L);
        savedBenefit.setBenefitCode("BEN001");
        savedBenefit.setBenefitName("Medical");

        BenefitResponse response = new BenefitResponse();
        response.setId(1L);
        response.setBenefitCode("BEN001");
        response.setBenefitName("Medical");

        when(benefitRepository.existsByBenefitCode("BEN001"))
                .thenReturn(false);

        when(enrollmentRepository.findById(1L))
                .thenReturn(Optional.of(enrollment));

        when(benefitMapper.toEntity(request))
                .thenReturn(benefit);

        when(benefitRepository.save(benefit))
                .thenReturn(savedBenefit);

        when(benefitMapper.toResponse(savedBenefit))
                .thenReturn(response);

        BenefitResponse result = benefitService.createBenefit(request);

        assertNotNull(result);
        assertEquals("BEN001", result.getBenefitCode());

        verify(benefitRepository).existsByBenefitCode("BEN001");
        verify(enrollmentRepository).findById(1L);
        verify(benefitRepository).save(benefit);

        verify(auditService).logEvent(
                eq("BENEFIT_CREATED"),
                eq("Benefit"),
                eq(1L),
                contains("Medical"),
                eq("SYSTEM"));
    }

    @Test
    void createBenefit_ShouldThrowException_WhenBenefitExists() {

        CreateBenefitRequest request = new CreateBenefitRequest();
        request.setBenefitCode("BEN001");

        when(benefitRepository.existsByBenefitCode("BEN001"))
                .thenReturn(true);

        RuntimeException ex =
                assertThrows(RuntimeException.class,
                        () -> benefitService.createBenefit(request));

        assertEquals("Benefit code already exists.", ex.getMessage());

        verify(benefitRepository).existsByBenefitCode("BEN001");
        verify(benefitRepository, never()).save(any());
    }

    @Test
    void createBenefit_ShouldThrowException_WhenEnrollmentNotFound() {

        CreateBenefitRequest request = new CreateBenefitRequest();
        request.setBenefitCode("BEN001");
        request.setEnrollmentId(1L);

        when(benefitRepository.existsByBenefitCode("BEN001"))
                .thenReturn(false);

        when(enrollmentRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex =
                assertThrows(RuntimeException.class,
                        () -> benefitService.createBenefit(request));

        assertEquals("Enrollment not found.", ex.getMessage());

        verify(enrollmentRepository).findById(1L);
        verify(benefitRepository, never()).save(any());
    }
}