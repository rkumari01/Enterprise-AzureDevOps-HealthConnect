
package com.healthconnect.enrollment.controller;

import com.healthconnect.enrollment.dto.request.CreateEnrollmentRequest;
import com.healthconnect.enrollment.dto.response.EnrollmentResponse;
import com.healthconnect.enrollment.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentResponse createEnrollment(
            @Valid @RequestBody CreateEnrollmentRequest request) {

        return enrollmentService.createEnrollment(request);
    }
}