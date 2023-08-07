package com.abnamro.flightapi.service.flight;

import com.abnamro.flightapi.database.yml.YMLDatabase;
import com.abnamro.flightapi.exception.FlightException;
import com.abnamro.flightapi.exception.FlightRequestException;
import com.abnamro.flightapi.mapper.FlightMapper;
import com.abnamro.flightapi.model.flight.Flight;
import com.abnamro.flightapi.model.flight.FlightRequest;
import com.abnamro.flightapi.model.flight.FlightResponse;
import com.abnamro.flightapi.utility.FlightUtility;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service class for Flight-related operations.
 */
@Service
@Slf4j
@Data
public class FlightService {

    @Autowired
    private YMLDatabase ymlDatabase;


    /**
     * Retrieves a list of flight responses based on the provided flight request parameters.
     *
     * @param flightRequest The flight request object containing the parameters for filtering flights.
     * @return A list of FlightResponse objects.
     */
    public List<FlightResponse> getFlightsWithParameters(FlightRequest flightRequest) {
        log.info("Building predicates for FlightRequest: {}", flightRequest);

        // Build a list of predicates based on the flightRequest and Flight class
        List<Predicate<Flight>> predicateList = FlightUtility.buildPredicates(flightRequest, Flight.class);

        Comparator<Flight> comparator;
        try {
            log.debug("Getting comparator for sortedBy field: {}", flightRequest.getSortedBy());

            // Get the comparator for sorting based on the sortedBy field in the flightRequest if sortedBy field exists for the Flight class
            comparator = FlightUtility.getComparatorByFieldName(Flight.class, flightRequest.getSortedBy());
        } catch (FlightRequestException e) {
            log.warn("FlightRequestException occurred while getting comparator. Using default sorting by flightNumber.");

            // If sortedBy field does not exist for the Flight class, default sorting will be by flightNumber
            comparator = Comparator.comparing(Flight::getFlightNumber);
        } catch (Exception e) {
            log.error("Exception occurred while getting comparator: {}", e.getMessage(), e);

            e.printStackTrace();
            throw e;
        }

        List<Flight> allFlights = ymlDatabase.getAllFlights();
        log.debug("Retrieved all flights from ymlDatabase. Total flights: {}", allFlights.size());

        return allFlights.stream() // Retrieve all flights from the ymlDatabase
                .filter(predicateList.stream().reduce(x -> true, Predicate::and)) // Apply all predicates to filter flights
                .sorted(comparator) // Sort the filtered flights using the provided comparator
                .map(FlightMapper::modelToView) // Convert each Flight object to a FlightResponse object using the mapper
                .collect(Collectors.toList()); // Collect the FlightResponse objects into a list
    }

}
