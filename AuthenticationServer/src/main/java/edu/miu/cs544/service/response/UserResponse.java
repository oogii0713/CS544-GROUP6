package edu.miu.cs544.service.response;

public class UserResponse {
	private Integer id;
	private String email;
	private String role;
	private Integer passengerId;
	private boolean isValid;

	public UserResponse(Integer id, String email, String role, Integer passengerId, boolean isValid) {
		this.id = id;
		this.email = email;
		this.role = role;
		this.passengerId = passengerId;
		this.isValid = isValid;
	}

    public UserResponse() {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public Integer getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(Integer passengerId) {
		this.passengerId = passengerId;
	}
}
