package com.healthconnect.payment.service.impl;

import com.healthconnect.audit.service.AuditService;
import com.healthconnect.claim.entity.Claim;
import com.healthconnect.claim.repository.ClaimRepository;
import com.healthconnect.payment.dto.request.CreatePaymentRequest;
import com.healthconnect.payment.dto.response.PaymentResponse;
import com.healthconnect.payment.entity.Payment;
import com.healthconnect.payment.mapper.PaymentMapper;
import com.healthconnect.payment.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ClaimRepository claimRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void createPayment_ShouldCreatePaymentSuccessfully() {

        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setPaymentReference("PAY001");
        request.setAmount(new BigDecimal("5000"));
        request.setPaymentDate(LocalDate.now());
        request.setPaymentMethod("UPI");
        request.setStatus("SUCCESS");
        request.setClaimId(1L);

        Claim claim = new Claim();

        Payment payment = new Payment();
        payment.setPaymentReference("PAY001");

        Payment savedPayment = new Payment();
        savedPayment.setId(1L);
        savedPayment.setPaymentReference("PAY001");

        PaymentResponse response = new PaymentResponse();
        response.setId(1L);
        response.setPaymentReference("PAY001");
        response.setAmount(new BigDecimal("5000"));

        when(paymentRepository.existsByPaymentReference("PAY001"))
                .thenReturn(false);

        when(claimRepository.findById(1L))
                .thenReturn(Optional.of(claim));

        when(paymentMapper.toEntity(request))
                .thenReturn(payment);

        when(paymentRepository.save(payment))
                .thenReturn(savedPayment);

        when(paymentMapper.toResponse(savedPayment))
                .thenReturn(response);

        PaymentResponse result = paymentService.createPayment(request);

        assertNotNull(result);
        assertEquals("PAY001", result.getPaymentReference());

        verify(paymentRepository).existsByPaymentReference("PAY001");
        verify(claimRepository).findById(1L);
        verify(paymentRepository).save(payment);

        verify(auditService).logEvent(
                eq("PAYMENT_COMPLETED"),
                eq("Payment"),
                eq(1L),
                contains("PAY001"),
                eq("SYSTEM"));
    }

    @Test
    void createPayment_ShouldThrowException_WhenReferenceExists() {

        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setPaymentReference("PAY001");

        when(paymentRepository.existsByPaymentReference("PAY001"))
                .thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> paymentService.createPayment(request));

        assertEquals("Payment reference already exists.", ex.getMessage());

        verify(paymentRepository).existsByPaymentReference("PAY001");
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void createPayment_ShouldThrowException_WhenClaimNotFound() {

        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setPaymentReference("PAY001");
        request.setClaimId(1L);

        when(paymentRepository.existsByPaymentReference("PAY001"))
                .thenReturn(false);

        when(claimRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> paymentService.createPayment(request));

        assertEquals("Claim not found.", ex.getMessage());

        verify(claimRepository).findById(1L);
        verify(paymentRepository, never()).save(any());
    }
}