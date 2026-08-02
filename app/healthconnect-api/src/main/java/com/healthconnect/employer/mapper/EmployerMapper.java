package com.healthconnect.employer.mapper;

import com.healthconnect.employer.dto.request.CreateEmployerRequest;
import com.healthconnect.employer.dto.response.EmployerResponse;
import com.healthconnect.employer.entity.Employer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployerMapper {

    @Mapping(target = "id", ignore = true)
    Employer toEntity(CreateEmployerRequest request);

    EmployerResponse toResponse(Employer employer);
}
