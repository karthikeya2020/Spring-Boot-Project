package com.event.security;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.event.services.UserService;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTTokenFilter extends OncePerRequestFilter {
	@Autowired
	public JWTUtility jwtUtility;
	@Autowired
	UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwtToken = request.getHeader("Authorization");

		// here we are checking weather the jwt token is null or not if it is null we will return
		// later if the api is login or register it will fillterd.
		if(jwtToken == null || !jwtToken.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = jwtToken.substring(7);
		String username = jwtUtility.getUserNameFromToken(token);
		// user name is null or not

		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails user = userService.loadUserByUsername(username);

			if(jwtUtility.isValidToken(token, username)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
		
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {

	    String path = request.getServletPath();

	    return path.startsWith("/swagger-ui/")
	            || path.startsWith("/v3/api-docs/")
	            || path.equals("/swagger-ui.html");
	}

}
