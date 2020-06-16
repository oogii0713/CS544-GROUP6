package edu.miu.cs544.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.miu.cs544.domain.ERole;
import edu.miu.cs544.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUserDetails implements UserDetails {

	private final Integer id;
	private final String username;
	private final String password;
	private final ERole role;
	private final Integer passengerId;

	public JwtUserDetails(Integer id, String username, String password, ERole role, Integer passengerId) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.passengerId = passengerId;
	}


	public static JwtUserDetails build(User user) {
		return new JwtUserDetails(
				user.getId(),
				user.getUsername(),
				user.getPassword(),
				user.getRole().getName(),
				user.getPassengerId());
	}

	@JsonIgnore
	public Integer getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		return authorities;
	}

	public String getRole() {
		return role.toString().substring(5);
	}

	public Integer getPassengerId() {
		return passengerId;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
