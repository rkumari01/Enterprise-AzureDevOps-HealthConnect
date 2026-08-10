package com.healthconnect.enrollment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthconnect.enrollment.dto.request.CreateEnrollmentRequest;
import com.healthconnect.enrollment.dto.response.EnrollmentResponse;
import com.healthconnect.enrollment.service.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnrollmentController.class)
class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EnrollmentService enrollmentService;

    @Test
    void createEnrollment_ShouldReturnCreatedEnrollment() throws Exception {

        CreateEnrollmentRequest request = new CreateEnrollmentRequest();
        request.setMemberId("MEM001");
        request.setPlanName("Gold Plan");
        request.setCoverageType("Family");
        request.setEffectiveDate(LocalDate.now());
        request.setStatus("ACTIVE");
        request.setUserId(1L);
        request.setEmployerId(1L);

        EnrollmentResponse response = new EnrollmentResponse();
        response.setId(1L);
        response.setMemberId("MEM001");
        response.setPlanName("Gold Plan");
        response.setCoverageType("Family");
        response.setStatus("ACTIVE");

        when(enrollmentService.createEnrollment(any(CreateEnrollmentRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.memberId").value("MEM001"))
                .andExpect(jsonPath("$.planName").value("Gold Plan"))
                .andExpect(jsonPath("$.coverageType").value("Family"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(enrollmentService, times(1))
                .createEnrollment(any(CreateEnrollmentRequest.class));
    }
}