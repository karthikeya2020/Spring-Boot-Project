package com.event.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
	
	@Autowired
	JWTTokenFilter jwtFilter;
	@Autowired
	private CustomAccessDeniedHandler accessDeniedHandler;
	
	@Bean
	public AuthenticationManager authenticationManager(
	AuthenticationConfiguration configuration)
	throws Exception {

	    return configuration.getAuthenticationManager();

	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	        	.requestMatchers("/public/login").permitAll() 
	            .requestMatchers("/public/register").permitAll() // Allow registration
	            .requestMatchers("/events/getEvents").permitAll()
	            .requestMatchers("/events/getEvent/**").permitAll()
	            .requestMatchers("/booking/viewAllBookings").hasRole("ADMIN")
	            .requestMatchers("/events/admin/createEvent").hasRole("ADMIN")
	            .requestMatchers("/events/admin/updateEvent").hasRole("ADMIN")
	            .requestMatchers("/events/admin/deleteEvent/*").hasRole("ADMIN")
	            .requestMatchers("/admin/viewAllUsers").hasRole("ADMIN")
	            .requestMatchers(
	                    "/swagger-ui/**",
	                    "/swagger-ui.html",
	                    "/v3/api-docs/**"
	                ).permitAll()
	            .anyRequest().authenticated()
	        )
	        .exceptionHandling(exception -> exception
	                .accessDeniedHandler(accessDeniedHandler)
	         )
	        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )

        .addFilterBefore(jwtFilter,
                UsernamePasswordAuthenticationFilter.class);
	    return http.build();
	}

}
