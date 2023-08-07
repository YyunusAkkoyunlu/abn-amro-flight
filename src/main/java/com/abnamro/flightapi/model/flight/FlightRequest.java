package com.abnamro.flightapi.model.flight;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequest {

    @NotBlank(message = "Origin shouldn't be empty")
    private String origin;

    private String destination;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    private float price;

    private String sortedBy = "";

}
