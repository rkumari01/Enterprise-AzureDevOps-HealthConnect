package com.healthconnect.employer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthconnect.employer.dto.request.CreateEmployerRequest;
import com.healthconnect.employer.dto.response.EmployerResponse;
import com.healthconnect.employer.service.EmployerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployerController.class)
class EmployerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployerService employerService;

    @Test
    void createEmployer_ShouldReturnCreatedEmployer() throws Exception {

        CreateEmployerRequest request = new CreateEmployerRequest();
        request.setEmployerCode("EMP001");
        request.setCompanyName("ABC Corp");
        request.setContactPerson("John Doe");
        request.setContactEmail("john@abc.com");
        request.setPhoneNumber("9999999999");
        request.setStatus("ACTIVE");

        EmployerResponse response = new EmployerResponse();
        response.setId(1L);
        response.setEmployerCode("EMP001");
        response.setCompanyName("ABC Corp");
        response.setContactPerson("John Doe");
        response.setContactEmail("john@abc.com");
        response.setPhoneNumber("9999999999");
        response.setStatus("ACTIVE");

        when(employerService.createEmployer(any(CreateEmployerRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/employers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.employerCode").value("EMP001"))
                .andExpect(jsonPath("$.companyName").value("ABC Corp"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(employerService, times(1))
                .createEmployer(any(CreateEmployerRequest.class));
    }
}