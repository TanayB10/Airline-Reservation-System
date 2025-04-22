package com.airline.reservation.dto;

import java.time.LocalDateTime;

public class BookingRequestDTO {

    private LocalDateTime bookingDate;
    private String status;
    private Long userId;
    private Long seatId;
    private Long flightId;

    // Constructors
    public BookingRequestDTO() {
    }

    public BookingRequestDTO(LocalDateTime bookingDate, String status, Long userId, Long seatId, Long flightId) {
        this.bookingDate = bookingDate;
        this.status = status;
        this.userId = userId;
        this.seatId = seatId;
        this.flightId = flightId;
    }

    // Getters and Setters

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
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
