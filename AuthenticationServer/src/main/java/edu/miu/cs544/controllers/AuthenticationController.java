package edu.miu.cs544.controllers;

import edu.miu.cs544.exception.AuthenticationException;
import edu.miu.cs544.exception.JwtTokenException;
import edu.miu.cs544.service.AuthenticationService;
import edu.miu.cs544.service.request.LoginRequest;
import edu.miu.cs544.service.response.JwtResponse;
import edu.miu.cs544.security.jwt.JwtUtils;
import edu.miu.cs544.service.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws AuthenticationException {
		Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String jwt = jwtUtils.generateJwtToken(authentication);
		return ResponseEntity.ok(new JwtResponse(jwt));
	}

	@PostMapping(value = "/validate")
	@PreAuthorize("hasRole('PASSENGER') or hasRole('ADMIN') or hasRole('AGENT')")
	public UserResponse validateToken(@RequestBody String token) {
		return authenticationService.validateToken(token);
	}

	@GetMapping(value = "/validate")
	public UserResponse validateToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer ")) {
			throw new JwtTokenException("No JWT token found in request headers.");
		}
		String token = header.substring(7);
		return authenticationService.validateToken(token);
	}

	private Authentication authenticate(String username, String password) {
		try {
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException exp) {
			throw new AuthenticationException("INVALID_CREDENTIALS", exp);
		}
	}

}
