package com.healthconnect.claim.mapper;

import com.healthconnect.claim.dto.request.CreateClaimRequest;
import com.healthconnect.claim.dto.response.ClaimResponse;
import com.healthconnect.claim.entity.Claim;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClaimMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollment", ignore = true)
    Claim toEntity(CreateClaimRequest request);

    @Mapping(target = "memberId", source = "enrollment.memberId")
    @Mapping(target = "employeeName",
            expression = "java(claim.getEnrollment().getUser().getFirstName() + \" \" + claim.getEnrollment().getUser().getLastName())")
    ClaimResponse toResponse(Claim claim);
}