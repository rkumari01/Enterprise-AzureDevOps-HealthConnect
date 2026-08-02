package com.healthconnect.payment.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentResponse {

    private Long id;

    private String paymentReference;

    private BigDecimal amount;

    private LocalDate paymentDate;

    private String paymentMethod;

    private String status;

    private String claimNumber;

    private String memberId;

    private String employeeName;
}