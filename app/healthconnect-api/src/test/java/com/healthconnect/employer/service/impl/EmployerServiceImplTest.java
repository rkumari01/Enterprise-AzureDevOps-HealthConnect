package com.healthconnect.employer.service.impl;

import com.healthconnect.audit.service.AuditService;
import com.healthconnect.employer.dto.request.CreateEmployerRequest;
import com.healthconnect.employer.dto.response.EmployerResponse;
import com.healthconnect.employer.entity.Employer;
import com.healthconnect.employer.mapper.EmployerMapper;
import com.healthconnect.employer.repository.EmployerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployerServiceImplTest {

    @Mock
    private EmployerRepository employerRepository;

    @Mock
    private EmployerMapper employerMapper;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private EmployerServiceImpl employerService;

    @Test
    void createEmployer_ShouldCreateEmployerSuccessfully() {

        CreateEmployerRequest request = new CreateEmployerRequest();
        request.setEmployerCode("EMP001");
        request.setCompanyName("ABC Corp");
        request.setContactPerson("John Doe");
        request.setContactEmail("john@abc.com");
        request.setPhoneNumber("9999999999");
        request.setStatus("ACTIVE");

        Employer employer = Employer.builder()
                .employerCode("EMP001")
                .companyName("ABC Corp")
                .contactPerson("John Doe")
                .contactEmail("john@abc.com")
                .phoneNumber("9999999999")
                .status("ACTIVE")
                .build();

        Employer savedEmployer = Employer.builder()
                .id(1L)
                .employerCode("EMP001")
                .companyName("ABC Corp")
                .contactPerson("John Doe")
                .contactEmail("john@abc.com")
                .phoneNumber("9999999999")
                .status("ACTIVE")
                .build();

        EmployerResponse response = new EmployerResponse();
        response.setId(1L);
        response.setEmployerCode("EMP001");
        response.setCompanyName("ABC Corp");
        response.setContactPerson("John Doe");
        response.setContactEmail("john@abc.com");
        response.setPhoneNumber("9999999999");
        response.setStatus("ACTIVE");

        when(employerMapper.toEntity(request)).thenReturn(employer);
        when(employerRepository.save(employer)).thenReturn(savedEmployer);
        when(employerMapper.toResponse(savedEmployer)).thenReturn(response);

        EmployerResponse result = employerService.createEmployer(request);

        assertNotNull(result);
        assertEquals("EMP001", result.getEmployerCode());
        assertEquals("ABC Corp", result.getCompanyName());

        verify(employerMapper).toEntity(request);
        verify(employerRepository).save(employer);
        verify(employerMapper).toResponse(savedEmployer);

        verify(auditService).logEvent(
                eq("EMPLOYER_CREATED"),
                eq("Employer"),
                eq(1L),
                contains("ABC Corp"),
                eq("SYSTEM")
        );
    }
}