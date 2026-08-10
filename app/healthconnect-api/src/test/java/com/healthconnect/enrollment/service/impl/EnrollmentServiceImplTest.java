package com.healthconnect.enrollment.service.impl;

import com.healthconnect.audit.service.AuditService;
import com.healthconnect.employer.entity.Employer;
import com.healthconnect.employer.repository.EmployerRepository;
import com.healthconnect.enrollment.dto.request.CreateEnrollmentRequest;
import com.healthconnect.enrollment.dto.response.EnrollmentResponse;
import com.healthconnect.enrollment.entity.Enrollment;
import com.healthconnect.enrollment.mapper.EnrollmentMapper;
import com.healthconnect.enrollment.repository.EnrollmentRepository;
import com.healthconnect.user.entity.User;
import com.healthconnect.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceImplTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmployerRepository employerRepository;

    @Mock
    private EnrollmentMapper enrollmentMapper;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @Test
    void createEnrollment_ShouldCreateEnrollmentSuccessfully() {

        CreateEnrollmentRequest request = new CreateEnrollmentRequest();
        request.setMemberId("MEM001");
        request.setPlanName("Gold Plan");
        request.setCoverageType("Family");
        request.setEffectiveDate(LocalDate.now());
        request.setStatus("ACTIVE");
        request.setUserId(1L);
        request.setEmployerId(1L);

        User user = new User();
        Employer employer = new Employer();

        Enrollment enrollment = new Enrollment();
        enrollment.setMemberId("MEM001");

        Enrollment savedEnrollment = new Enrollment();
        savedEnrollment.setId(1L);
        savedEnrollment.setMemberId("MEM001");

        EnrollmentResponse response = new EnrollmentResponse();
        response.setId(1L);
        response.setMemberId("MEM001");

        when(enrollmentRepository.existsByMemberId("MEM001"))
                .thenReturn(false);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(employerRepository.findById(1L))
                .thenReturn(Optional.of(employer));

        when(enrollmentMapper.toEntity(request))
                .thenReturn(enrollment);

        when(enrollmentRepository.save(enrollment))
                .thenReturn(savedEnrollment);

        when(enrollmentMapper.toResponse(savedEnrollment))
                .thenReturn(response);

        EnrollmentResponse result = enrollmentService.createEnrollment(request);

        assertNotNull(result);
        assertEquals("MEM001", result.getMemberId());

        verify(enrollmentRepository).existsByMemberId("MEM001");
        verify(userRepository).findById(1L);
        verify(employerRepository).findById(1L);
        verify(enrollmentRepository).save(enrollment);

        verify(auditService).logEvent(
                eq("ENROLLMENT_CREATED"),
                eq("Enrollment"),
                eq(1L),
                contains("MEM001"),
                eq("SYSTEM"));
    }

    @Test
    void createEnrollment_ShouldThrowException_WhenMemberExists() {

        CreateEnrollmentRequest request = new CreateEnrollmentRequest();
        request.setMemberId("MEM001");

        when(enrollmentRepository.existsByMemberId("MEM001"))
                .thenReturn(true);

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> enrollmentService.createEnrollment(request));

        assertEquals("Member ID already exists.", exception.getMessage());

        verify(enrollmentRepository).existsByMemberId("MEM001");
        verify(enrollmentRepository, never()).save(any());
    }
}