package com.healthconnect.benefit.service;

import com.healthconnect.benefit.dto.request.CreateBenefitRequest;
import com.healthconnect.benefit.dto.response.BenefitResponse;

public interface BenefitService {

    BenefitResponse createBenefit(CreateBenefitRequest request);
}