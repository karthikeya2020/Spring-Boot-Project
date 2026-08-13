package com.event.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.event.entities.Events;
import com.event.entities.User;
import com.event.repositories.EventRepository;

@Service
public class EventService {
	@Autowired
	private EventRepository eventRepository;
	
	public String registerEvent(User user,Events event) {
		event.setCreatedBy(user);
		Events savedEvent = eventRepository.save(event);
		return "Event " + savedEvent.getTitle() +" sucessfully registered ";
	}
	
	public String changeEvent(User user,Events event) {
		Events existingEvent = eventRepository.findById(event.getEventId())
	            .orElseThrow(() ->
	                new RuntimeException(
	                    "Event not found with id: " + event.getEventId()
	                )
	            );

	    event.setCreatedBy(user);

	    Events updatedEvent = eventRepository.save(event);

	    return "Event with Id " + updatedEvent.getEventId()
	            + " is successfully changed";
	}
	
	public String deleteEvent(int eventId) {
		Events isPresent = eventRepository.findById(eventId).orElseThrow(() 
				-> new RuntimeException("Event not found with id " + eventId));
		
		eventRepository.deleteById(eventId);
		return "Sucessfully deleted the Event";
	}
	
	public List<Events> getAllEvents(){
		List allEvents = eventRepository.findAll();
		return allEvents;
	}
	
	public Events getEvent(int eventId) {
		return eventRepository.findById(eventId)
	            .orElseThrow(() -> new RuntimeException("Event not found with id: " + eventId));
		
	}
	
	public List<Events> findByUser(int usserId){
		return eventRepository.findByCreatedBy(usserId);
	}

}
