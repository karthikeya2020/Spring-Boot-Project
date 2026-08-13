package com.event.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.event.entities.Booking;
import com.event.entities.User;
import com.event.services.BookingService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/booking")
public class BookingController {
	@Autowired
	BookingService bookingService;
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/bookEvent/{eventId}/{numOfSeats}")
	public Booking bookEvent(@PathVariable int eventId,@PathVariable int numOfSeats , @AuthenticationPrincipal User user) {
		return bookingService.bookEvent(user, eventId, numOfSeats);
	}
	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/viewMyBookings")
	public List<Booking> viewUserBookings(@AuthenticationPrincipal User user){
		return bookingService.userBookings(user);
	}
	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/cancelBooking/{bookingId}")
	public String cancelBooking(@PathVariable int bookingId) {
		return bookingService.cancelBooking(bookingId);
	}
	@SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/viewAllBookings")
	public List<Booking> viewAllBookings(){
		return bookingService.allBookings();
	}

}
