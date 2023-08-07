package com.abnamro.flightapi.controller.flight;

import com.abnamro.flightapi.model.flight.FlightRequest;
import com.abnamro.flightapi.model.flight.FlightResponse;
import com.abnamro.flightapi.service.flight.FlightService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for Flight-related API endpoints.
 */
@RestController
@RequestMapping("/api/flight")
@Slf4j
public class FlightController {

    @Autowired
    private FlightService flightService;


    /**
     * Retrieves a list of flight responses based on the provided flight request parameters.
     *
     * @param flightRequest The flight request object containing the parameters for filtering flights.
     * @return A ResponseEntity containing a list of FlightResponse objects.
     */
    @PostMapping("/getFlightsWithParameters")
    public ResponseEntity<List<FlightResponse>> getFlightsWithParameters(@Valid @RequestBody FlightRequest flightRequest) {
        log.info("Received request to get flights with parameters. FlightRequest: {}", flightRequest);

        // Call the flightService to retrieve a list of flight responses based on the provided flightRequest
        List<FlightResponse> flightResponseList = flightService.getFlightsWithParameters(flightRequest);
        // Log the flightResponseList for seeing on the log file
        log.debug("Flight response list retrieved: {}", flightResponseList);

        log.info("Returning flight response list with {} flights as response.", flightResponseList.size());
        // Return the flightResponseList as a response entity with HTTP status OK (200)
        return new ResponseEntity<>(flightResponseList, HttpStatus.OK);
    }

}
