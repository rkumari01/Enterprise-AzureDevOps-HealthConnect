package com.healthconnect.enrollment.service;

import com.healthconnect.enrollment.dto.request.CreateEnrollmentRequest;
import com.healthconnect.enrollment.dto.response.EnrollmentResponse;

public interface EnrollmentService {

    EnrollmentResponse createEnrollment(CreateEnrollmentRequest request);
}
