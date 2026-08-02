package com.healthconnect.benefit.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateBenefitRequest {

    @NotBlank
    private String benefitCode;

    @NotBlank
    private String benefitName;

    private String description;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal coverageAmount;

    @NotNull
    private Boolean isActive;

    @NotNull
    private Long enrollmentId;
}