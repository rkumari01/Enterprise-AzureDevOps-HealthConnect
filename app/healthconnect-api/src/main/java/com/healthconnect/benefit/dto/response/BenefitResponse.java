package com.healthconnect.benefit.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BenefitResponse {

    private Long id;

    private String benefitCode;

    private String benefitName;

    private String description;

    private BigDecimal coverageAmount;

    private Boolean isActive;

    private String memberId;

    private String employeeName;
}