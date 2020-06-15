package edu.miu.cs544.controllers;

import edu.miu.cs544.service.AuthenticationService;
import edu.miu.cs544.service.request.LoginRequest;
import edu.miu.cs544.service.response.JwtResponse;
import edu.miu.cs544.security.jwt.JwtUtils;
import edu.miu.cs544.service.response.TokenValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import edu.miu.cs544.exception.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthenticationController {
	@Autowired
    AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String jwt = jwtUtils.generateJwtToken(authentication);
		return ResponseEntity.ok(new JwtResponse(jwt));
	}

	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public TokenValidationResponse validateToken(@RequestBody String token) {
		return authenticationService.validateToken(token);
	}

	private Authentication authenticate(String username, String password) {
		try {
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("INVALID_CREDENTIALS", e);
		}
	}

}
