package com.healthconnect.payment.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreatePaymentRequest {

    @NotBlank
    private String paymentReference;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotNull
    private LocalDate paymentDate;

    @NotBlank
    private String paymentMethod;

    @NotBlank
    private String status;

    @NotNull
    private Long claimId;
}