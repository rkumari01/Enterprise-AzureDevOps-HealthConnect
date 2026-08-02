package com.healthconnect.role.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleResponse {

    private Long id;
    private String roleName;
    private String description;
    private LocalDateTime createdAt;
}