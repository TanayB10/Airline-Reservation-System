package com.airline.reservation.dto;

public class SeatResponseDTO {

    private Long id; // The unique identifier for the seat
    private String seatNumber;
    private String seatClass;
    private boolean available;
    private Long flightId; // The ID of the associated flight

    // Constructor
    public SeatResponseDTO(Long id, String seatNumber, String seatClass, boolean available, Long flightId) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.available = available;
        this.flightId = flightId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
}
