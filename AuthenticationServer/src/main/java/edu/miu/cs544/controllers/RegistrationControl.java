package edu.miu.cs544.controllers;

import edu.miu.cs544.domain.ERole;
import edu.miu.cs544.exception.EmailAlreadyExistException;
import edu.miu.cs544.service.RegistrationService;
import edu.miu.cs544.service.request.SignupRequest;
import edu.miu.cs544.service.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/sign-up")
public class RegistrationControl {

    @Autowired
    private RegistrationService registrationService;


    @PostMapping("/passenger")
    public ResponseEntity<?> registerPassenger(@Valid @RequestBody SignupRequest signUpRequest) throws EmailAlreadyExistException, URISyntaxException {
        registrationService.saveUser(signUpRequest, ERole.ROLE_PASSENGER);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/agent")
    public ResponseEntity<?> registerAgent(@Valid @RequestBody SignupRequest signUpRequest) throws EmailAlreadyExistException, URISyntaxException {
        registrationService.saveUser(signUpRequest, ERole.ROLE_AGENT);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
