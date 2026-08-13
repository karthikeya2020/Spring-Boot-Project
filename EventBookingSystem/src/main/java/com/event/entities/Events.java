package com.event.entities;

import java.util.Date;


import org.hibernate.annotations.AnyDiscriminatorImplicitValues.Strategy;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table (name = "Events")
@Data
public class Events {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int eventId;
	@NotNull
	private String title;
	
	private String description;
	@NotNull
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date eventDate;
	@NotNull
	private int totalSeats;
	private int availableSeats;
	@ManyToOne
	@JoinColumn (name = "created_by")
	private User createdBy;
}
