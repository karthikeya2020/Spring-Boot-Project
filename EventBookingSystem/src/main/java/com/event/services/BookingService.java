package com.event.services;

import java.time.LocalDateTime;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.event.entities.Booking;
import com.event.entities.Events;
import com.event.entities.User;
import com.event.repositories.BookingRepository;
import com.event.repositories.EventRepository;

@Service
public class BookingService {
	@Autowired
	EventRepository eventRepository;
	@Autowired
	BookingRepository bookingRepository;
	public Booking bookEvent(User currentUser, int eventId, int numOfSeats) {
		Events eventDetails = eventRepository.findById(eventId).orElseThrow(() 
				-> new RuntimeException("Event not found with Id " +eventId + " please select appropriate event"));
		if (eventDetails.getAvailableSeats()<numOfSeats){
			throw new RuntimeException("Not Enough seats avilable in this event");
		}
		
		eventDetails.setAvailableSeats(eventDetails.getAvailableSeats() - numOfSeats);
		eventRepository.save(eventDetails);
		
		Booking booking = new Booking();
		booking.setUser(currentUser);
		booking.setEvent(eventDetails);
		booking.setNumOfSeats(numOfSeats);
		booking.setStatus("conformed");
		booking.setBookingDate(LocalDateTime.now());
		return bookingRepository.save(booking);
		
	}
	
	public List<Booking> userBookings(User currentUser){
		List<Booking> userBookings = bookingRepository.findByUser(currentUser);
		return userBookings;
	}
	
	public String cancelBooking(int bookingId) {
		Booking bookedEvent = bookingRepository.findById(bookingId).orElseThrow(()
				-> new RuntimeException("No Event booked on that Id" +bookingId));
		
		int seats = bookedEvent.getNumOfSeats();
		Events event = bookedEvent.getEvent();
		event.setAvailableSeats(event.getAvailableSeats()+seats);
		eventRepository.save(event);
		bookedEvent.setStatus("canceled");
		bookingRepository.save(bookedEvent);
		return "Booked event is Canceled !";
	}
	
	public List<Booking> allBookings(){
		return bookingRepository.findAll();
	}
}
