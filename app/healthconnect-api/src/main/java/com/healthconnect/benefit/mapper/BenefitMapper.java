package com.healthconnect.benefit.mapper;

import com.healthconnect.benefit.dto.request.CreateBenefitRequest;
import com.healthconnect.benefit.dto.response.BenefitResponse;
import com.healthconnect.benefit.entity.Benefit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BenefitMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollment", ignore = true)
    Benefit toEntity(CreateBenefitRequest request);

    @Mapping(target = "memberId", source = "enrollment.memberId")
    @Mapping(
        target = "employeeName",
        expression = "java(benefit.getEnrollment().getUser().getFirstName() + \" \" + benefit.getEnrollment().getUser().getLastName())"
    )
    BenefitResponse toResponse(Benefit benefit);
}