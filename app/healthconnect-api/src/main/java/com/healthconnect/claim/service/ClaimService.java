package com.healthconnect.claim.service;

import com.healthconnect.claim.dto.request.CreateClaimRequest;
import com.healthconnect.claim.dto.response.ClaimResponse;

public interface ClaimService {

    ClaimResponse createClaim(CreateClaimRequest request);
}