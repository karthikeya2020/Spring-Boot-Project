package com.event.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.event.entities.Events;
@Repository
public interface EventRepository extends JpaRepository<Events, Integer> {
	List<Events> findByCreatedBy(int userId);
}
