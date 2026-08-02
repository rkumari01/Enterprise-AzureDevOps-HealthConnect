package com.healthconnect.claim.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ClaimResponse {

    private Long id;
    private String claimNumber;
    private String hospitalName;
    private BigDecimal claimAmount;
    private LocalDate claimDate;
    private String diagnosis;
    private String status;
    private String remarks;

    private String memberId;
    private String employeeName;
}