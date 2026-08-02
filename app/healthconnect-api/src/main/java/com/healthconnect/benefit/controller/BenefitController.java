package com.healthconnect.benefit.controller;

import com.healthconnect.benefit.dto.request.CreateBenefitRequest;
import com.healthconnect.benefit.dto.response.BenefitResponse;
import com.healthconnect.benefit.service.BenefitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/benefits")
@RequiredArgsConstructor
public class BenefitController {

    private final BenefitService benefitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BenefitResponse createBenefit(
            @Valid @RequestBody CreateBenefitRequest request) {

        return benefitService.createBenefit(request);
    }
}