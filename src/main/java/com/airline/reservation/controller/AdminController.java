package com.airline.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airline.reservation.dto.CrewMemberRequestDTO;
import com.airline.reservation.dto.CrewMemberResponseDTO;
import com.airline.reservation.dto.FlightRequestDTO;
import com.airline.reservation.dto.FlightResponseDTO;
import com.airline.reservation.dto.FlightUpdateRequestDTO;
import com.airline.reservation.dto.PlaneRequestDTO;
import com.airline.reservation.dto.PlaneResponseDTO;
import com.airline.reservation.dto.SeatRequestDTO;
import com.airline.reservation.dto.SeatResponseDTO;
import com.airline.reservation.model.Admin;
import com.airline.reservation.model.Flight;
import com.airline.reservation.model.Plane;
import com.airline.reservation.repository.FlightRepository;
import com.airline.reservation.repository.PlaneRepository;
import com.airline.reservation.service.AdminService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    private PlaneRepository planeRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Create Admin
    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin newAdmin = adminService.createAdmin(admin);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    }

    // Get Admin by Username
    @GetMapping("/{username}")
    public ResponseEntity<Admin> getAdminByUsername(@PathVariable String username) {
        Admin admin = adminService.findAdminByUsername(username);
        return admin != null
                ? new ResponseEntity<>(admin, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/login")
    public ResponseEntity<String> adminLogin(@RequestBody Admin request) {
        // Validate credentials or whatever the logic is
        if (adminService.validLogin(request)) {
            return ResponseEntity.ok("Admin login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Add Flight
    @PostMapping("/flight")
    public ResponseEntity<FlightResponseDTO> addFlight(@Valid @RequestBody FlightRequestDTO dto) {
        Flight flight = new Flight();
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setArrival(dto.getArrival());
        flight.setDeparture(dto.getDeparture());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setDepartureTime(dto.getDepartureTime());

        Plane plane = planeRepository.findById(dto.getPlaneId())
                .orElseThrow(() -> new RuntimeException("Plane not found"));
        flight.setPlane(plane);

        flightRepository.save(flight);

        FlightResponseDTO responseDTO = new FlightResponseDTO(
                flight.getId(),
                flight.getFlightNumber(),
                flight.getArrival(),
                flight.getDeparture(),
                flight.getArrivalTime(),
                flight.getDepartureTime(),
                plane.getId()
        );

        return ResponseEntity.ok(responseDTO);
    }

    // Update Flight
    @PutMapping("/flight/{id}")
    public ResponseEntity<FlightResponseDTO> updateFlight(@PathVariable Long id, @RequestBody FlightUpdateRequestDTO dto) {
        // Call the service to get the updated flight response DTO
        FlightResponseDTO updatedFlightDTO = adminService.updateFlight(id, dto);

        // Return the updated flight response DTO
        return ResponseEntity.ok(updatedFlightDTO);
    }

    // Delete Flight
    @DeleteMapping("/flight/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        adminService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    // Get All Flights
    @GetMapping("/flights")
    public ResponseEntity<List<FlightResponseDTO>> getAllFlights() {
        List<FlightResponseDTO> flights = adminService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    // Add Seat to Flight
    @PostMapping("/flight/{flightId}/seat")
    public ResponseEntity<SeatResponseDTO> addSeatToFlight(@PathVariable Long flightId, @RequestBody SeatRequestDTO seatRequestDTO) {
        // Call the service to create a seat for the flight
        SeatResponseDTO seatResponseDTO = adminService.addSeatToFlight(flightId, seatRequestDTO);
        
        return new ResponseEntity<>(seatResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/seat/{seatId}")
    public ResponseEntity<SeatResponseDTO> updateSeat(@PathVariable Long seatId, @RequestBody SeatRequestDTO seatRequestDTO) {
        SeatResponseDTO updatedSeat = adminService.updateSeat(seatId, seatRequestDTO);
        return ResponseEntity.ok(updatedSeat);
    }

    // Get All Seats
    @GetMapping("/seats")
    public ResponseEntity<List<SeatResponseDTO>> getAllSeats() {
        List<SeatResponseDTO> seats = adminService.getAllSeats();
        return ResponseEntity.ok(seats);
    }

   
    // Add Crew Member
    @PostMapping("/crew")
    public ResponseEntity<CrewMemberResponseDTO> addCrewMember(@RequestBody CrewMemberRequestDTO crewMemberRequestDTO) {
        CrewMemberResponseDTO newCrewMember = adminService.addCrewMember(crewMemberRequestDTO);
        return ResponseEntity.ok(newCrewMember);
    }   


    // Get All Crew Members
    @GetMapping("/crew")
    public ResponseEntity<List<CrewMemberResponseDTO>> getAllCrewMembers() {
        List<CrewMemberResponseDTO> crewMembers = adminService.getAllCrewMembers();
        return ResponseEntity.ok(crewMembers);
    }


    // Assign Crew to Flight
    @PostMapping("/flight/{flightId}/crew")
    public ResponseEntity<CrewMemberResponseDTO> assignCrew(@PathVariable Long flightId, @RequestBody CrewMemberRequestDTO crewMemberRequestDTO) {
        CrewMemberResponseDTO crewMember = adminService.assignCrewToFlight(flightId, crewMemberRequestDTO);
        return ResponseEntity.ok(crewMember);
    }

    @PutMapping("/crew/{id}")
    public ResponseEntity<CrewMemberResponseDTO> updateCrewMember(@PathVariable Long id, @RequestBody CrewMemberRequestDTO crewMemberRequestDTO) {
        CrewMemberResponseDTO updatedCrewMember = adminService.updateCrewMember(id, crewMemberRequestDTO);
        return ResponseEntity.ok(updatedCrewMember);
    }


   

    // Add Plane
    @PostMapping("/plane")
    public ResponseEntity<PlaneResponseDTO> addPlane(@RequestBody PlaneRequestDTO planeRequestDTO) {
        PlaneResponseDTO response = adminService.addPlane(planeRequestDTO);
        return ResponseEntity.ok(response);
    }

    // Update Plane
    @PutMapping("/plane/{id}")
    public ResponseEntity<PlaneResponseDTO> updatePlane(@PathVariable Long id, @RequestBody PlaneRequestDTO planeRequestDTO) {
        PlaneResponseDTO response = adminService.updatePlane(id, planeRequestDTO);
        return ResponseEntity.ok(response);
    }

    // Delete Plane
    @DeleteMapping("/plane/{id}")
    public ResponseEntity<Void> deletePlane(@PathVariable Long id) {
        adminService.deletePlane(id);
        return ResponseEntity.noContent().build();
    }

    // Get All Planes
    @GetMapping("/planes")
    public ResponseEntity<List<PlaneResponseDTO>> getAllPlanes() {
        List<PlaneResponseDTO> planes = adminService.getAllPlanes();
        return ResponseEntity.ok(planes);
    }
}
