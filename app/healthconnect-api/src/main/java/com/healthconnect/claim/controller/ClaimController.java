package com.healthconnect.claim.controller;

import com.healthconnect.claim.dto.request.CreateClaimRequest;
import com.healthconnect.claim.dto.response.ClaimResponse;
import com.healthconnect.claim.service.ClaimService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClaimResponse createClaim(
            @Valid @RequestBody CreateClaimRequest request) {

        return claimService.createClaim(request);
    }
}