package com.airline.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airline.reservation.model.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
