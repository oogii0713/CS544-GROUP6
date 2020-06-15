package edu.miu.cs544.service.response;

public class TokenValidationResponse {

	private String username;
	private String role;
	private boolean isValid;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
}
