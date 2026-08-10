package com.healthconnect.role.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthconnect.role.dto.request.CreateRoleRequest;
import com.healthconnect.role.dto.response.RoleResponse;
import com.healthconnect.role.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoleService roleService;

    @Test
    void createRole_ShouldReturnCreatedRole() throws Exception {

        CreateRoleRequest request = new CreateRoleRequest();
        request.setRoleName("ADMIN");
        request.setDescription("Administrator");

        RoleResponse response = new RoleResponse();
        response.setId(1L);
        response.setRoleName("ADMIN");
        response.setDescription("Administrator");
        response.setCreatedAt(LocalDateTime.now());

        when(roleService.createRole(any(CreateRoleRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.roleName").value("ADMIN"))
                .andExpect(jsonPath("$.description").value("Administrator"));

        verify(roleService, times(1))
                .createRole(any(CreateRoleRequest.class));
    }

    @Test
    void getAllRoles_ShouldReturnRoleList() throws Exception {

        RoleResponse admin = new RoleResponse();
        admin.setId(1L);
        admin.setRoleName("ADMIN");
        admin.setDescription("Administrator");

        RoleResponse user = new RoleResponse();
        user.setId(2L);
        user.setRoleName("USER");
        user.setDescription("Normal User");

        when(roleService.getAllRoles())
                .thenReturn(List.of(admin, user));

        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].roleName").value("ADMIN"))
                .andExpect(jsonPath("$[1].roleName").value("USER"));

        verify(roleService).getAllRoles();
    }

    @Test
    void getRoleByName_ShouldReturnRole() throws Exception {

        RoleResponse response = new RoleResponse();
        response.setId(1L);
        response.setRoleName("ADMIN");
        response.setDescription("Administrator");

        when(roleService.getRoleByName("ADMIN"))
                .thenReturn(response);

        mockMvc.perform(get("/api/roles/ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value("ADMIN"))
                .andExpect(jsonPath("$.description").value("Administrator"));

        verify(roleService).getRoleByName("ADMIN");
    }
}