package com.airline.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airline.reservation.model.Plane;

public interface PlaneRepository extends JpaRepository<Plane, Long> {
}
