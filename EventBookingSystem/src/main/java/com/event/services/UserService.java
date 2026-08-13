package com.event.services;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.event.entities.User;
import com.event.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired 
	private UserRepository userRepo;
	
	public List<User> allUsers(){ 
		return userRepo.findByRole("USER");
	}
	
	public List<User> allAdmins(){
		return userRepo.findByRole("ADMIN");
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userInfo = userRepo.findByUsername(username);
		if(userInfo.isPresent()) {
			return (UserDetails) userInfo.get();
		}
		else {
			throw new UsernameNotFoundException("user Not found");
		}
	}

}
