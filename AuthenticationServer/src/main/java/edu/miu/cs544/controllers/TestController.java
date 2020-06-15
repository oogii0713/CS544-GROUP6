package edu.miu.cs544.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/test")
public class TestController {
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/passenger")
	@PreAuthorize("hasRole('PASSENGER') or hasRole('ADMIN')")
	public String passengerAccess() {
		return "Hi Passenger.";
	}

	@GetMapping("/agent")
	@PreAuthorize("hasRole('AGENT')")
	public String agentAccess() {
		return "Hi Agent.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Hi ADMIN.";
	}
}
