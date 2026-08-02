package com.healthconnect.employer.service.impl;

import com.healthconnect.audit.service.AuditService;
import com.healthconnect.employer.dto.request.CreateEmployerRequest;
import com.healthconnect.employer.dto.response.EmployerResponse;
import com.healthconnect.employer.entity.Employer;
import com.healthconnect.employer.mapper.EmployerMapper;
import com.healthconnect.employer.repository.EmployerRepository;
import com.healthconnect.employer.service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepository employerRepository;
    private final EmployerMapper employerMapper;
    private final AuditService auditService;

    @Override
    public EmployerResponse createEmployer(CreateEmployerRequest request) {

        Employer employer = employerMapper.toEntity(request);

        Employer savedEmployer = employerRepository.save(employer);
        auditService.logEvent(
        "EMPLOYER_CREATED",
        "Employer",
        savedEmployer.getId(),
        "Employer " + savedEmployer.getCompanyName() + " created.",
        "SYSTEM");

        return employerMapper.toResponse(savedEmployer);
    }
}