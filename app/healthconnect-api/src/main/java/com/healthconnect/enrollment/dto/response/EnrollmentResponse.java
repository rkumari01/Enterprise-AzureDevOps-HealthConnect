package com.healthconnect.enrollment.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EnrollmentResponse {

    private Long id;
    private String memberId;
    private String planName;
    private String coverageType;
    private LocalDate effectiveDate;
    private String status;

    private String employeeName;
    private String employerName;
}
