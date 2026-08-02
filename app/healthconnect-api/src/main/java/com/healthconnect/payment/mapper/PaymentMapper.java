package com.healthconnect.payment.mapper;

import com.healthconnect.payment.dto.request.CreatePaymentRequest;
import com.healthconnect.payment.dto.response.PaymentResponse;
import com.healthconnect.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "claim", ignore = true)
    Payment toEntity(CreatePaymentRequest request);

    @Mapping(target = "claimNumber", source = "claim.claimNumber")
    @Mapping(target = "memberId", source = "claim.enrollment.memberId")
    @Mapping(
        target = "employeeName",
        expression = "java(payment.getClaim().getEnrollment().getUser().getFirstName() + \" \" + payment.getClaim().getEnrollment().getUser().getLastName())"
    )
    PaymentResponse toResponse(Payment payment);
}