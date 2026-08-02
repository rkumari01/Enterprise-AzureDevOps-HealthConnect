package com.healthconnect.employer.controller;

import com.healthconnect.employer.dto.request.CreateEmployerRequest;
import com.healthconnect.employer.dto.response.EmployerResponse;
import com.healthconnect.employer.service.EmployerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employers")
@RequiredArgsConstructor
public class EmployerController {

    private final EmployerService employerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployerResponse createEmployer(@Valid @RequestBody CreateEmployerRequest request) {
        return employerService.createEmployer(request);
    }
}
