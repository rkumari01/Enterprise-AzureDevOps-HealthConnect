package com.healthconnect.role.service;

import com.healthconnect.role.dto.request.CreateRoleRequest;
import com.healthconnect.role.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(CreateRoleRequest request);

    List<RoleResponse> getAllRoles();

    RoleResponse getRoleByName(String roleName);
}