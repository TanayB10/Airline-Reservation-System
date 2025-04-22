package com.airline.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.airline.reservation")
@EntityScan(basePackages = "com.airline.reservation.model")
public class AirlineReservationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AirlineReservationApplication.class, args);
    }
}
