package com.event.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.event.entities.Events;
import com.event.entities.User;
import com.event.services.EventService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/events")
public class EventController {
	private final BookingController bookingController;
	@Autowired
	private EventService eventService;

	EventController(BookingController bookingController) {
		this.bookingController = bookingController;
	}
	
	@GetMapping("/getEvents")
	public List<Events> getAllEvents() {
		return eventService.getAllEvents();
	}
	@GetMapping("/getEvent/{eventid}")
	public Events getEvent(@PathVariable int eventid) {
		return eventService.getEvent(eventid);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/admin/createEvent")
	public String createEvent(@AuthenticationPrincipal User user,@RequestBody Events event) {
		System.out.println("creater event");
		return eventService.registerEvent(user,event);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/admin/updateEvent")
	public String updateEvent(@AuthenticationPrincipal User user,@RequestBody Events event) {
		return eventService.changeEvent(user,event);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	@DeleteMapping("/admin/deleteEvent/{eventid}")
	public String removeEvent(@PathVariable int eventid) {
		return eventService.deleteEvent(eventid);
	}
	
	
}
