package com.healthconnect.employer.service;

import com.healthconnect.employer.dto.request.CreateEmployerRequest;
import com.healthconnect.employer.dto.response.EmployerResponse;

public interface EmployerService {

    EmployerResponse createEmployer(CreateEmployerRequest request);

}