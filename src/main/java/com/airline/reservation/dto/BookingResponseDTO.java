package com.airline.reservation.dto;

import java.time.LocalDateTime;

public class BookingResponseDTO {

    private Long id;
    private LocalDateTime bookingDate;
    private LocalDateTime bookingTime;
    private String status;
    private Long userId;
    private Long seatId;
    private Long flightId;

    // Default Constructor
    public BookingResponseDTO() {}

    // Constructor with parameters
    public BookingResponseDTO(Long id, LocalDateTime bookingDate, LocalDateTime bookingTime, String status,
                              Long userId, Long seatId, Long flightId) {
        this.id = id;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.status = status;
        this.userId = userId;
        this.seatId = seatId;
        this.flightId = flightId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }
}
