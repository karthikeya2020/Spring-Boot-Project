package com.event.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Login {
	private String username;
	private String password;
}
