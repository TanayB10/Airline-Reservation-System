package com.airline.reservation.dto;

public class PlaneRequestDTO {
    private String model;
    private int capacity;
    private String manufacturer;

    // Getters & Setters
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getManufacturer() {
        return manufacturer;
    }
}
