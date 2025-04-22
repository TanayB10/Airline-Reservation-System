package com.airline.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airline.reservation.model.CrewMember;

public interface CrewMemberRepository extends JpaRepository<CrewMember, Long> {
}
