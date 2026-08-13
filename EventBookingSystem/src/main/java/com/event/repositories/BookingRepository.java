package com.event.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.event.entities.Booking;
import com.event.entities.User;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
	List<Booking> findByUser(User user);
}
