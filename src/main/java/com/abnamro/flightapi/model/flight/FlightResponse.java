package com.abnamro.flightapi.model.flight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightResponse {

    private String flightNumber;

    private String origin;

    private String destination;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    private String price;

}
