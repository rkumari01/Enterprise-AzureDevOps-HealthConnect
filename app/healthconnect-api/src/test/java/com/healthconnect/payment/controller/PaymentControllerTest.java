package com.healthconnect.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthconnect.payment.dto.request.CreatePaymentRequest;
import com.healthconnect.payment.dto.response.PaymentResponse;
import com.healthconnect.payment.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    @Test
    void createPayment_ShouldReturnCreatedPayment() throws Exception {

        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setPaymentReference("PAY001");
        request.setAmount(new BigDecimal("5000"));
        request.setPaymentDate(LocalDate.now());
        request.setPaymentMethod("UPI");
        request.setStatus("SUCCESS");
        request.setClaimId(1L);

        PaymentResponse response = new PaymentResponse();
        response.setId(1L);
        response.setPaymentReference("PAY001");
        response.setAmount(new BigDecimal("5000"));
        response.setPaymentDate(LocalDate.now());
        response.setPaymentMethod("UPI");
        response.setStatus("SUCCESS");
        response.setClaimNumber("CLM001");
        response.setMemberId("MEM001");
        response.setEmployeeName("John Doe");

        when(paymentService.createPayment(any(CreatePaymentRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.paymentReference").value("PAY001"))
                .andExpect(jsonPath("$.paymentMethod").value("UPI"))
                .andExpect(jsonPath("$.status").value("SUCCESS"));

        verify(paymentService, times(1))
                .createPayment(any(CreatePaymentRequest.class));
    }
}