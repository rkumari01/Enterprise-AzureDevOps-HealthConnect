package com.healthconnect.role.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRoleRequest {

    @NotBlank(message = "Role Name is required")
    private String roleName;

    @NotBlank(message = "Description is required")
    private String description;
}
