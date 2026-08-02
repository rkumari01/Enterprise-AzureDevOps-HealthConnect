package com.healthconnect.employer.dto.response;

import lombok.Data;

@Data
public class EmployerResponse {

    private Long id;
    private String employerCode;
    private String companyName;
    private String contactPerson;
    private String contactEmail;
    private String phoneNumber;
    private String status;
}
