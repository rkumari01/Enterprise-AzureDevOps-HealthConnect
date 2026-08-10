package com.healthconnect.benefit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthconnect.benefit.dto.request.CreateBenefitRequest;
import com.healthconnect.benefit.dto.response.BenefitResponse;
import com.healthconnect.benefit.service.BenefitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BenefitController.class)
class BenefitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BenefitService benefitService;

    @Test
    void createBenefit_ShouldReturnCreatedBenefit() throws Exception {

        CreateBenefitRequest request = new CreateBenefitRequest();
        request.setBenefitCode("BEN001");
        request.setBenefitName("Medical");
        request.setDescription("Medical Coverage");
        request.setCoverageAmount(new BigDecimal("50000"));
        request.setEnrollmentId(1L);
        request.setIsActive(true);

        BenefitResponse response = new BenefitResponse();
        response.setId(1L);
        response.setBenefitCode("BEN001");
        response.setBenefitName("Medical");
        response.setDescription("Medical Coverage");
        response.setCoverageAmount(new BigDecimal("50000"));
        response.setIsActive(true);

        when(benefitService.createBenefit(any(CreateBenefitRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/benefits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.benefitCode").value("BEN001"))
                .andExpect(jsonPath("$.benefitName").value("Medical"))
                .andExpect(jsonPath("$.description").value("Medical Coverage"));

        verify(benefitService, times(1))
                .createBenefit(any(CreateBenefitRequest.class));
    }
}