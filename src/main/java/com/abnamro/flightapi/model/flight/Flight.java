package com.abnamro.flightapi.model.flight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

    private String flightNumber;

    private String origin;

    private String destination;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    private float price;

}
