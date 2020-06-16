package edu.miu.cs544.service;

import edu.miu.cs544.service.response.UserResponse;

public interface AuthenticationService {
    UserResponse validateToken(String token);
}
