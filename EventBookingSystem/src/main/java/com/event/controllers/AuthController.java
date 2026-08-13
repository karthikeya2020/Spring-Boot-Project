package com.event.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.event.dto.Login;
import com.event.entities.User;
import com.event.security.JWTUtility;
import com.event.services.AuthService;

@RestController
@RequestMapping("/public")
public class AuthController {
	@Autowired
	private AuthService authService;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JWTUtility jwtUtility;
	@PostMapping ("/login")
	public ResponseEntity<?> login(@RequestBody Login login) {
		try {
				Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                    login.getUsername(),
	                    login.getPassword()
	            )
	    );

	    if (authentication.isAuthenticated()) {
	    	String token = jwtUtility.createToken(login.getUsername());

            return ResponseEntity.ok(token);
	    }
	    return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Login failed");

    } catch (BadCredentialsException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username or password");
    }
	}
	
	@PostMapping("/register")
	public String register(@RequestBody User user) {
		System.out.println("request called");
		return authService.addUser(user);
	}

}
