package com.airline.reservation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.reservation.dto.BookingRequestDTO;
import com.airline.reservation.dto.BookingResponseDTO;
import com.airline.reservation.model.Booking;
import com.airline.reservation.model.Flight;
import com.airline.reservation.model.Seat;
import com.airline.reservation.model.User;
import com.airline.reservation.repository.BookingRepository;
import com.airline.reservation.repository.FlightRepository;
import com.airline.reservation.repository.SeatRepository;
import com.airline.reservation.repository.UserRepository;
import com.airline.reservation.util.CreditCardPayment;
import com.airline.reservation.util.PayPalPayment;
import com.airline.reservation.util.PaymentContext;

@Service
public class UserService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(BookingRepository bookingRepository,
                       FlightRepository flightRepository,
                       SeatRepository seatRepository,
                       UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
    }

    public BookingResponseDTO bookFlight(BookingRequestDTO dto) {
    User user = userRepository.findById(dto.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    Seat seat = seatRepository.findById(dto.getSeatId())
        .orElseThrow(() -> new RuntimeException("Seat not found"));

    Flight flight = flightRepository.findById(dto.getFlightId())
        .orElseThrow(() -> new RuntimeException("Flight not found"));

        // Optional: mark seat as unavailable
        seat.setAvailable(false);
        seatRepository.save(seat);

        Booking booking = new Booking();
        booking.setBookingDate(dto.getBookingDate());
        booking.setStatus(dto.getStatus());
        booking.setUser(user);
        booking.setSeat(seat);
        booking.setFlight(flight);

        Booking savedBooking = bookingRepository.save(booking);

        BookingResponseDTO response = new BookingResponseDTO();
        response.setId(savedBooking.getId());
        response.setBookingDate(savedBooking.getBookingDate());
        response.setStatus(savedBooking.getStatus());
        response.setUserId(user.getId());
        response.setSeatId(seat.getId());
        response.setFlightId(flight.getId());

        return response;
    }

    public List<BookingResponseDTO> getUserBookingDTOs(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        return bookings.stream().map(booking -> new BookingResponseDTO(
            booking.getId(),
            booking.getBookingDate(),  // Use the booking date from the booking
            booking.getBookingTime(),  // Ensure this is set correctly
            booking.getStatus(),
            booking.getUser().getId(), // Map user ID correctly
            booking.getSeat().getId(), // Map seat ID correctly
            booking.getFlight().getId() // Map flight ID correctly
        )).collect(Collectors.toList());
    }

    public String processPayment(Long bookingId, String method) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        PaymentContext context = new PaymentContext();

        switch (method.toLowerCase()) {
            case "credit":
                context.setStrategy(new CreditCardPayment());
                break;
            case "paypal":
                context.setStrategy(new PayPalPayment());
                break;
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + method);
        }

        String result = context.processPayment(199.99); // mock price

        // Update status (simulate successful payment)
        booking.setStatus("PAID");
        bookingRepository.save(booking);

        return result + " Booking status updated to PAID.";
    }

    public String cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            return "Booking is already cancelled.";
        }

        booking.setStatus("CANCELLED");

        Seat seat = booking.getSeat();
        seat.setAvailable(true);

        seatRepository.save(seat);
        bookingRepository.save(booking);

        return "Booking cancelled successfully. Seat is now available.";
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}
