package com.healthconnect.enrollment.mapper;

import com.healthconnect.enrollment.dto.request.CreateEnrollmentRequest;
import com.healthconnect.enrollment.dto.response.EnrollmentResponse;
import com.healthconnect.enrollment.entity.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "employer", ignore = true)
    Enrollment toEntity(CreateEnrollmentRequest request);

    @Mapping(target = "employeeName",
            expression = "java(enrollment.getUser().getFirstName() + \" \" + enrollment.getUser().getLastName())")
    @Mapping(target = "employerName",
            source = "employer.companyName")
    EnrollmentResponse toResponse(Enrollment enrollment);
}
