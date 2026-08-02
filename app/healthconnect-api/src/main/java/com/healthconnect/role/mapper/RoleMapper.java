package com.healthconnect.role.mapper;

import com.healthconnect.role.dto.request.CreateRoleRequest;
import com.healthconnect.role.dto.response.RoleResponse;
import com.healthconnect.role.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Role toEntity(CreateRoleRequest request);

    RoleResponse toResponse(Role role);
}