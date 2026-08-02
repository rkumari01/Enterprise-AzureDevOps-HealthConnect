package com.healthconnect.employer.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateEmployerRequest {

    @NotBlank
    private String employerCode;

    @NotBlank
    private String companyName;

    @NotBlank
    private String contactPerson;

    @Email
    @NotBlank
    private String contactEmail;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String status;
}
