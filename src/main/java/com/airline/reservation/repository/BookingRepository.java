// BookingRepository.java
package com.airline.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airline.reservation.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
}
