package com.airline.reservation.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//import org.antlr.v4.runtime.misc.NotNull;

public class FlightRequestDTO {

    //@JsonProperty("flight_number")
    @NotBlank
    private String flightNumber;

    private String arrival;
    private String departure;

    //@JsonProperty("arrival_time")
    private LocalDateTime arrivalTime;

    //@JsonProperty("departure_time")
    @NotNull
    private LocalDateTime departureTime;

    //@JsonProperty("plane_id")
    @NotNull
    private Long planeId;

    // Getters and setters

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
}
