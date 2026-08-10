package com.healthconnect.claim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthconnect.claim.dto.request.CreateClaimRequest;
import com.healthconnect.claim.dto.response.ClaimResponse;
import com.healthconnect.claim.service.ClaimService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClaimController.class)
class ClaimControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClaimService claimService;

    @Test
    void createClaim_ShouldReturnCreatedClaim() throws Exception {

        CreateClaimRequest request = new CreateClaimRequest();
        request.setClaimNumber("CLM001");
        request.setHospitalName("Apollo Hospital");
        request.setClaimAmount(new BigDecimal("2500"));
        request.setClaimDate(LocalDate.now());
        request.setDiagnosis("Fever");
        request.setStatus("SUBMITTED");
        request.setRemarks("First Claim");
        request.setEnrollmentId(1L);

        ClaimResponse response = new ClaimResponse();
        response.setId(1L);
        response.setClaimNumber("CLM001");
        response.setHospitalName("Apollo Hospital");
        response.setClaimAmount(new BigDecimal("2500"));
        response.setClaimDate(LocalDate.now());
        response.setDiagnosis("Fever");
        response.setStatus("SUBMITTED");
        response.setRemarks("First Claim");
        response.setMemberId("MEM001");
        response.setEmployeeName("John Doe");

        when(claimService.createClaim(any(CreateClaimRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/claims")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.claimNumber").value("CLM001"))
                .andExpect(jsonPath("$.hospitalName").value("Apollo Hospital"))
                .andExpect(jsonPath("$.claimAmount").value(2500))
                .andExpect(jsonPath("$.diagnosis").value("Fever"))
                .andExpect(jsonPath("$.status").value("SUBMITTED"))
                .andExpect(jsonPath("$.remarks").value("First Claim"))
                .andExpect(jsonPath("$.memberId").value("MEM001"))
                .andExpect(jsonPath("$.employeeName").value("John Doe"));

        verify(claimService, times(1))
                .createClaim(any(CreateClaimRequest.class));
    }
}