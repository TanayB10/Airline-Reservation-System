package com.airline.reservation.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.airline.reservation.model.CrewMember;
import com.airline.reservation.model.Flight;
import com.airline.reservation.model.Plane;
import com.airline.reservation.model.Seat;
import com.airline.reservation.repository.AdminRepository;
import com.airline.reservation.repository.CrewMemberRepository;
import com.airline.reservation.repository.FlightRepository;
import com.airline.reservation.repository.PlaneRepository;
import com.airline.reservation.repository.SeatRepository;

@Service
public class AdminService {

    @Autowired
    private final FlightRepository flightRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final AdminRepository adminRepository;
    private final PlaneRepository planeRepository;
    @Autowired
    private final SeatRepository seatRepository; // Added seat repository

    @Autowired
    public AdminService(FlightRepository flightRepository, CrewMemberRepository crewMemberRepository, 
                        AdminRepository adminRepository, PlaneRepository planeRepository, SeatRepository seatRepository) {
        this.flightRepository = flightRepository;
        this.crewMemberRepository = crewMemberRepository;
        this.adminRepository = adminRepository;
        this.planeRepository = planeRepository;
        this.seatRepository = seatRepository; // Initialize seat repository
    }

    // Create Admin
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // Find Admin by Username
    public Admin findAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

     // Method to validate admin login credentials
     public boolean validLogin(Admin request) {
        // Check if the admin with the given username exists
        Admin admin = adminRepository.findByUsername(request.getUsername());

        // If admin exists and password matches, return true, else false
        return admin != null && admin.getPassword().equals(request.getPassword());
    }

    // Add Flight (with DTO)
    public FlightResponseDTO addFlight(FlightRequestDTO dto) {
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

        // Convert to DTO
        return new FlightResponseDTO(
                flight.getId(),
                flight.getFlightNumber(),
                flight.getArrival(),
                flight.getDeparture(),
                flight.getArrivalTime(),
                flight.getDepartureTime(),
                flight.getPlane() != null ? flight.getPlane().getId() : null
        );
    }

    // Update Flight (with DTO)
    public FlightResponseDTO updateFlight(Long id, FlightUpdateRequestDTO dto) {
        Optional<Flight> flightOpt = flightRepository.findById(id);
        if (flightOpt.isPresent()) {
            Flight flight = flightOpt.get();
            flight.setFlightNumber(dto.getFlightNumber());
            flight.setDeparture(dto.getDeparture());
            flight.setArrival(dto.getArrival());
            flight.setDepartureTime(dto.getDepartureTime());
            flight.setArrivalTime(dto.getArrivalTime());

            // Update the plane based on the planeId from DTO
            Plane plane = planeRepository.findById(dto.getPlaneId())
                    .orElseThrow(() -> new RuntimeException("Plane not found"));
            flight.setPlane(plane);

            flightRepository.save(flight);

            // Convert to DTO
            return new FlightResponseDTO(
                    flight.getId(),
                    flight.getFlightNumber(),
                    flight.getArrival(),
                    flight.getDeparture(),
                    flight.getArrivalTime(),
                    flight.getDepartureTime(),
                    flight.getPlane() != null ? flight.getPlane().getId() : null
            );
        }
        throw new RuntimeException("Flight not found");
    }

    // Delete Flight
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    public CrewMemberResponseDTO addCrewMember(CrewMemberRequestDTO crewMemberRequestDTO) {
        CrewMember crewMember = new CrewMember();
        crewMember.setName(crewMemberRequestDTO.getName());
        crewMember.setRole(crewMemberRequestDTO.getRole());
    
        // If you want to associate a crew member with a flight
        if (crewMemberRequestDTO.getFlightId() != null) {
            Flight flight = flightRepository.findById(crewMemberRequestDTO.getFlightId())
                    .orElseThrow(() -> new RuntimeException("Flight not found"));
            crewMember.setFlight(flight);
        }
    
        crewMember = crewMemberRepository.save(crewMember);
    
        return new CrewMemberResponseDTO(
                crewMember.getId(),
                crewMember.getName(),
                crewMember.getRole(),
                crewMember.getFlight() != null ? crewMember.getFlight().getFlightNumber() : null
        );
    }
    

    public List<CrewMemberResponseDTO> getAllCrewMembers() {
        List<CrewMember> crewMembers = crewMemberRepository.findAll();
        return crewMembers.stream()
                .map(crewMember -> new CrewMemberResponseDTO(
                        crewMember.getId(),
                        crewMember.getName(),
                        crewMember.getRole(),
                        crewMember.getFlight() != null ? crewMember.getFlight().getFlightNumber() : null
                ))
                .collect(Collectors.toList());
    }

    // Assign Crew to Flight (with DTO)
    public CrewMemberResponseDTO assignCrewToFlight(Long flightId, CrewMemberRequestDTO dto) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        CrewMember crewMember = new CrewMember();
        crewMember.setName(dto.getName());
        crewMember.setRole(dto.getRole());
        crewMember.setFlight(flight);

        crewMember = crewMemberRepository.save(crewMember);

        // Convert to DTO
        return new CrewMemberResponseDTO(
                crewMember.getId(),
                crewMember.getName(),
                crewMember.getRole(),
                crewMember.getFlight() != null ? crewMember.getFlight().getFlightNumber() : null
        );
    }

    public CrewMemberResponseDTO updateCrewMember(Long id, CrewMemberRequestDTO crewMemberRequestDTO) {
        CrewMember crewMember = crewMemberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crew member not found"));
    
        // Update the crew member's details
        crewMember.setName(crewMemberRequestDTO.getName());
        crewMember.setRole(crewMemberRequestDTO.getRole());
    
        // If needed, update the flight as well (ensure the flight exists)
        if (crewMemberRequestDTO.getFlightId() != null) {
            Flight flight = flightRepository.findById(crewMemberRequestDTO.getFlightId())
                    .orElseThrow(() -> new RuntimeException("Flight not found"));
            crewMember.setFlight(flight);
        }
    
        crewMember = crewMemberRepository.save(crewMember);
    
        // Return the updated crew member as a DTO
        return new CrewMemberResponseDTO(
                crewMember.getId(),
                crewMember.getName(),
                crewMember.getRole(),
                crewMember.getFlight() != null ? crewMember.getFlight().getFlightNumber() : null
        );
    }
    

    // Get All Flights
    public List<FlightResponseDTO> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        return flights.stream()
                .map(flight -> new FlightResponseDTO(
                        flight.getId(),
                        flight.getFlightNumber(),
                        flight.getArrival(),
                        flight.getDeparture(),
                        flight.getArrivalTime(),
                        flight.getDepartureTime(),
                        flight.getPlane() != null ? flight.getPlane().getId() : null
                ))
                .collect(Collectors.toList());
    }

    // Add Plane (with DTO)
    public PlaneResponseDTO addPlane(PlaneRequestDTO dto) {
        Plane plane = new Plane();
        plane.setModel(dto.getModel());
        plane.setManufacturer(dto.getManufacturer());
        plane.setCapacity(dto.getCapacity());

        plane = planeRepository.save(plane);

        // Convert to DTO
        return new PlaneResponseDTO(
                plane.getId(),
                plane.getModel(),
                plane.getCapacity()
        );
    }

    // Update Plane (with DTO)
    public PlaneResponseDTO updatePlane(Long id, PlaneRequestDTO dto) {
        Plane plane = planeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plane not found"));

        plane.setModel(dto.getModel());
        plane.setManufacturer(dto.getManufacturer());
        plane.setCapacity(dto.getCapacity());

        plane = planeRepository.save(plane);

        // Convert to DTO
        return new PlaneResponseDTO(
                plane.getId(),
                plane.getModel(),
                plane.getCapacity()
        );
    }

    // Delete Plane
    public void deletePlane(Long id) {
        planeRepository.deleteById(id);
    }

    // Get All Planes
    public List<PlaneResponseDTO> getAllPlanes() {
        List<Plane> planes = planeRepository.findAll();
        return planes.stream()
                .map(plane -> new PlaneResponseDTO(
                        plane.getId(),
                        plane.getModel(),
                        plane.getCapacity()
                ))
                .collect(Collectors.toList());
    }

    // Add Seat to Flight (with DTO)
    public SeatResponseDTO addSeatToFlight(Long flightId, SeatRequestDTO seatRequestDTO) {
        // Get the flight by its ID
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        // Create a new seat object
        Seat seat = new Seat();
        seat.setSeatNumber(seatRequestDTO.getSeatNumber());
        seat.setSeatClass(seatRequestDTO.getSeatClass());
        seat.setAvailable(seatRequestDTO.isAvailable());
        seat.setFlight(flight);

        seat = seatRepository.save(seat);

        // Convert to SeatResponseDTO and return
        return new SeatResponseDTO(
                seat.getId(),
                seat.getSeatNumber(),
                seat.getSeatClass(),
                seat.isAvailable(),
                flight.getId()
        );
    }

    public SeatResponseDTO updateSeat(Long seatId, SeatRequestDTO dto) {
        Seat seat = seatRepository.findById(seatId)
            .orElseThrow(() -> new RuntimeException("Seat not found"));
    
        seat.setSeatNumber(dto.getSeatNumber());
        seat.setSeatClass(dto.getSeatClass());
        seat.setAvailable(dto.isAvailable());
    
        seatRepository.save(seat);
    
        return new SeatResponseDTO(seat.getId(), seat.getSeatNumber(), seat.getSeatClass(), seat.isAvailable(), seat.getFlight().getId());

    }
    

    // Get All Seats
    public List<SeatResponseDTO> getAllSeats() {
        List<Seat> seats = seatRepository.findAll();
        return seats.stream()
                .map(seat -> new SeatResponseDTO(
                        seat.getId(),
                        seat.getSeatNumber(),
                        seat.getSeatClass(),
                        seat.isAvailable(),
                        seat.getFlight().getId()
                ))
                .collect(Collectors.toList());
    }
}
