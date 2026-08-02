
package com.healthconnect.user.mapper;

import com.healthconnect.user.dto.request.CreateUserRequest;
import com.healthconnect.user.dto.response.UserResponse;
import com.healthconnect.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(CreateUserRequest request);

    @Mapping(source = "role.roleName", target = "roleName")
    UserResponse toResponse(User user);
}