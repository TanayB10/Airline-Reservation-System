package com.airline.reservation.dto;

public class CrewMemberResponseDTO {
    private Long id;
    private String name;
    private String role;
    private String flightNumber;

    // Parameterized Constructor
    public CrewMemberResponseDTO(Long id, String name, String role, String flightNumber) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.flightNumber = flightNumber;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}
