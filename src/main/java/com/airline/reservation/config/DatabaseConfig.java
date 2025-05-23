package com.airline.reservation.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/airline_db")
                .username("root")
                .password("Tanay123")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}