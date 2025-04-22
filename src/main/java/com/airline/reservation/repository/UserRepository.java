package com.airline.reservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airline.reservation.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom queries if needed
    Optional<User> findByUsername(String username);
}
