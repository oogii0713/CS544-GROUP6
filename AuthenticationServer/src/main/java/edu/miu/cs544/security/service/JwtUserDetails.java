package edu.miu.cs544.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import edu.miu.cs544.domain.ERole;
import edu.miu.cs544.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUserDetails implements UserDetails {

	private final Integer id;
	private final String email;
	private final String password;
	private final ERole role;

	public JwtUserDetails(Integer id, String email, String password, ERole role) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.role = role;
	}


	public static JwtUserDetails build(User user) {
		return new JwtUserDetails(
				user.getId(),
				user.getEmail(),
				user.getPassword(),
				user.getRole().getName());
	}

	@JsonIgnore
	public Integer getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return email;
	}

	public String getEmail() {
		return email;
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
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		return authorities;
	}

	public String getRole() {
		return role.toString();
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
