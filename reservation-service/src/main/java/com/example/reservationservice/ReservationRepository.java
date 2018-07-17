package com.example.reservationservice;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
	
	@RestResource(path = "by-name")
	Collection<Reservation> findByReservationName(@Param("rn") String rn);

}
