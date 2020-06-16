package edu.miu.cs544.controllers;

import edu.miu.cs544.domain.ERole;
import edu.miu.cs544.exception.EmailAlreadyExistException;
import edu.miu.cs544.service.RegistrationService;
import edu.miu.cs544.service.request.UserRequest;
import edu.miu.cs544.service.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/sign-up")
public class RegistrationControl {

    @Autowired
    private RegistrationService registrationService;

    @PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
    @PostMapping("/passenger")
    public UserResponse registerPassenger(@Valid @RequestBody UserRequest signUpRequest) {
        try {
            return registrationService.saveUser(signUpRequest, ERole.ROLE_PASSENGER);
        } catch (EmailAlreadyExistException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Email is already registered!", ex);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/agent")
    public UserResponse registerAgent(@Valid @RequestBody UserRequest signUpRequest) {
        try {
            return registrationService.saveUser(signUpRequest, ERole.ROLE_AGENT);
        } catch (EmailAlreadyExistException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Email is already registered!", ex);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public UserResponse registerAdmin(@Valid @RequestBody UserRequest signUpRequest) {
        try {
            return registrationService.saveUser(signUpRequest, ERole.ROLE_ADMIN);
        } catch (EmailAlreadyExistException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Email is already registered!", ex);
        }
    }

}
