package com.healthconnect.claim.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateClaimRequest {

    @NotBlank
    private String claimNumber;

    @NotBlank
    private String hospitalName;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal claimAmount;

    @NotNull
    private LocalDate claimDate;

    @NotBlank
    private String diagnosis;

    @NotBlank
    private String status;

    private String remarks;

    @NotNull
    private Long enrollmentId;
}