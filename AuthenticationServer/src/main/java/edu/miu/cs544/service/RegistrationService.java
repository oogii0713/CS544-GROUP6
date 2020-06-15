package edu.miu.cs544.service;

import edu.miu.cs544.domain.ERole;
import edu.miu.cs544.exception.EmailAlreadyExistException;
import edu.miu.cs544.service.request.SignupRequest;

public interface RegistrationService {
    void saveUser(SignupRequest signupRequest, ERole roleType) throws EmailAlreadyExistException;
}
