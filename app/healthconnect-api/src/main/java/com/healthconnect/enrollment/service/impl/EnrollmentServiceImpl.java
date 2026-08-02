package com.healthconnect.enrollment.service.impl;

import com.healthconnect.audit.service.AuditService;
import com.healthconnect.employer.entity.Employer;
import com.healthconnect.employer.repository.EmployerRepository;
import com.healthconnect.enrollment.dto.request.CreateEnrollmentRequest;
import com.healthconnect.enrollment.dto.response.EnrollmentResponse;
import com.healthconnect.enrollment.entity.Enrollment;
import com.healthconnect.enrollment.mapper.EnrollmentMapper;
import com.healthconnect.enrollment.repository.EnrollmentRepository;
import com.healthconnect.enrollment.service.EnrollmentService;
import com.healthconnect.user.entity.User;
import com.healthconnect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final AuditService auditService;

    @Override
    public EnrollmentResponse createEnrollment(CreateEnrollmentRequest request) {

        if (enrollmentRepository.existsByMemberId(request.getMemberId())) {
            throw new RuntimeException("Member ID already exists.");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found."));

        Employer employer = employerRepository.findById(request.getEmployerId())
                .orElseThrow(() -> new RuntimeException("Employer not found."));

        Enrollment enrollment = enrollmentMapper.toEntity(request);

        enrollment.setUser(user);
        enrollment.setEmployer(employer);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        auditService.logEvent(
        "ENROLLMENT_CREATED",
        "Enrollment",
        savedEnrollment.getId(),
        "Enrollment created for member " + savedEnrollment.getMemberId(),
        "SYSTEM");

        return enrollmentMapper.toResponse(savedEnrollment);
    }
}
