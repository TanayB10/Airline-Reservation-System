package com.airline.reservation.controller;

import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airline.reservation.dto.BookingRequestDTO;
import com.airline.reservation.dto.BookingResponseDTO;
import com.airline.reservation.dto.SeatRequestDTO;
import com.airline.reservation.dto.SeatResponseDTO;
import com.airline.reservation.model.Flight;
import com.airline.reservation.model.Seat;
import com.airline.reservation.model.User;
import com.airline.reservation.repository.FlightRepository;
import com.airline.reservation.repository.SeatRepository;
import com.airline.reservation.repository.UserRepository;
import com.airline.reservation.service.AdminService;
import com.airline.reservation.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private AdminService adminService;

    public UserController(UserService userService, SeatRepository seatRepository, UserRepository userRepository) {
        this.userService = userService;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
    }

    // Register a new user
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(loginRequest.getPassword())) {
                // Return userId along with the message
                return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "userId", user.getId()
                ));
            } else {
                return ResponseEntity.status(401).body("Invalid password");
            }
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @GetMapping("/flights")
    public List<Flight> getAllAvailableFlights() {
        return flightRepository.findAll();
    }

    // Book a flight
    @PostMapping("/book")
    public ResponseEntity<BookingResponseDTO> bookFlight(@RequestBody BookingRequestDTO bookingRequest) {
        System.out.println("Booking Request: " + bookingRequest);
        // Log the flightId and seatId to verify they are valid
        System.out.println("flightId: " + bookingRequest.getFlightId());
        System.out.println("seatId: " + bookingRequest.getSeatId());
        BookingResponseDTO bookingResponse = userService.bookFlight(bookingRequest);
        return ResponseEntity.ok(bookingResponse);
    }

    @PostMapping("/flight/{flightId}/seat")
    public ResponseEntity<SeatResponseDTO> addSeatToFlight(@PathVariable Long flightId, @RequestBody SeatRequestDTO seatRequestDTO) {
        SeatResponseDTO seatResponseDTO = adminService.addSeatToFlight(flightId, seatRequestDTO);
        return new ResponseEntity<>(seatResponseDTO, HttpStatus.CREATED);
    }

    // Get available seats
    @GetMapping("/seats/{flightId}")
    public List<Seat> getAvailableSeats(@PathVariable Long flightId) {
        return seatRepository.findByFlightIdAndAvailableTrue(flightId);
    }

    // Get all bookings for a user
    @GetMapping("/bookings/{userId}")
    public List<BookingResponseDTO> getUserBookings(@PathVariable Long userId) {
        return userService.getUserBookingDTOs(userId);
    }

    // Cancel a booking
    @DeleteMapping("/cancel/{bookingId}")
    public String cancelBooking(@PathVariable Long bookingId) {
        return userService.cancelBooking(bookingId);
    }

    // Delete a user
    @DeleteMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "User with ID " + userId + " has been deleted.";
    }
}
