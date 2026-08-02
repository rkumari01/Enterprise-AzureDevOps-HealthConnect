package com.healthconnect.user.service;

import com.healthconnect.user.dto.request.CreateUserRequest;
import com.healthconnect.user.dto.response.UserResponse;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

}
