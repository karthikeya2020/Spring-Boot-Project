package com.event.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.event.dto.Login;
import com.event.entities.User;
import com.event.repositories.UserRepository;

@Service
public class AuthService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public String addUser(User user) {
		String password = user.getPassword();
		String encodedPassword = passwordEncoder.encode(password);
		user.setPassword(encodedPassword);
		User savedUser = userRepo.save(user);
		return savedUser.getUsername() + "is sucessfully registered";
	}
	

}
