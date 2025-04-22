package com.airline.reservation.dto;

import java.time.LocalDateTime;
import java.util.List;

public class FlightResponseDTO {
    private Long id;
    private String flightNumber;
    private String departure;
    private String arrival;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Long planeId;
    private List<String> crewMemberNames; // Optional

    // ✅ Parameterized Constructor
    public FlightResponseDTO(Long id, String flightNumber, String departure, String arrival,
                             LocalDateTime departureTime, LocalDateTime arrivalTime, Long planeId) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.arrival = arrival;
        this.departure = departure;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.planeId = planeId;
    }

    // ✅ Optional Constructor (with crew members)
    public FlightResponseDTO(Long id, String flightNumber, String departure, String arrival,
                             LocalDateTime departureTime, LocalDateTime arrivalTime,
                             Long planeId, List<String> crewMemberNames) {
        this(id, flightNumber, departure, arrival, departureTime, arrivalTime, planeId);
        this.crewMemberNames = crewMemberNames;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Long getPlaneId() {
        return planeId;
    }

    public void setPlaneId(Long planeId) {
        this.planeId = planeId;
    }

    public List<String> getCrewMemberNames() {
        return crewMemberNames;
    }

    public void setCrewMemberNames(List<String> crewMemberNames) {
        this.crewMemberNames = crewMemberNames;
    }
}
