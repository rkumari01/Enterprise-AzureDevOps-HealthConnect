package com.healthconnect.payment.service.impl;

import com.healthconnect.audit.service.AuditService;
import com.healthconnect.claim.entity.Claim;
import com.healthconnect.claim.repository.ClaimRepository;
import com.healthconnect.payment.dto.request.CreatePaymentRequest;
import com.healthconnect.payment.dto.response.PaymentResponse;
import com.healthconnect.payment.entity.Payment;
import com.healthconnect.payment.mapper.PaymentMapper;
import com.healthconnect.payment.repository.PaymentRepository;
import com.healthconnect.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ClaimRepository claimRepository;
    private final PaymentMapper paymentMapper;
    private final AuditService auditService;

    @Override
    public PaymentResponse createPayment(CreatePaymentRequest request) {

        if (paymentRepository.existsByPaymentReference(request.getPaymentReference())) {
            throw new RuntimeException("Payment reference already exists.");
        }

        Claim claim = claimRepository.findById(request.getClaimId())
                .orElseThrow(() -> new RuntimeException("Claim not found."));

        Payment payment = paymentMapper.toEntity(request);

        payment.setClaim(claim);

        Payment savedPayment = paymentRepository.save(payment);
        auditService.logEvent(
        "PAYMENT_COMPLETED",
        "Payment",
        savedPayment.getId(),
        "Payment " + savedPayment.getPaymentReference() + " completed.",
        "SYSTEM");

        return paymentMapper.toResponse(savedPayment);
    }
}