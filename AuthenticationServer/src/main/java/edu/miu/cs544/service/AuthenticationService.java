package edu.miu.cs544.service;

import edu.miu.cs544.service.response.TokenValidationResponse;

public interface AuthenticationService {
    TokenValidationResponse validateToken(String token);
}
