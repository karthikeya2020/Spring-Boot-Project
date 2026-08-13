package com.event.entities;

import java.util.Collection;

import java.util.List;
import java.util.Locale.Category;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder.Default;
import lombok.Data;

@Entity
@Table (name = "User")
@Component
@Data
public class User implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@NotNull
	private String username;
	@NotNull
	private String password;
	private String role;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		  return List.of(new SimpleGrantedAuthority("ROLE_" + role));
	}
}
