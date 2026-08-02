package com.healthconnect.enrollment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateEnrollmentRequest {

    @NotBlank
    private String memberId;

    @NotBlank
    private String planName;

    @NotBlank
    private String coverageType;

    @NotNull
    private LocalDate effectiveDate;

    @NotBlank
    private String status;

    @NotNull
    private Long userId;

    @NotNull
    private Long employerId;
}
