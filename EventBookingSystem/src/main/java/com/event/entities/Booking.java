package com.event.entities;

import java.time.LocalDateTime;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Entity
@Table(name = "Booking")
@Data
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Events event;
	private int numOfSeats;
	private LocalDateTime bookingDate;
	
	private String status;
	
}
