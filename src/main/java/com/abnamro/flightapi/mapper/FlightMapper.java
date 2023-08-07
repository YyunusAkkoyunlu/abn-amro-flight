package com.abnamro.flightapi.mapper;

import com.abnamro.flightapi.model.flight.Flight;
import com.abnamro.flightapi.model.flight.FlightResponse;
import com.abnamro.flightapi.utility.FlightUtility;

import java.util.Locale;

/**
 * Utility class for mapping Flight objects to FlightResponse objects.
 */
public class FlightMapper {

    /**
     * Converts a Flight object to a FlightResponse object.
     *
     * @param flight The Flight object to be converted.
     * @return The converted FlightResponse object.
     */
    public static FlightResponse modelToView(Flight flight) {
        // Check if the Flight object is null
        if (flight == null) {
            return null;
        }

        // Build and return the FlightResponse object based on the Flight object
        return FlightResponse.builder()
                    .flightNumber(flight.getFlightNumber())
                    .origin(flight.getOrigin())
                    .destination(flight.getDestination())
                    .departureTime(flight.getDepartureTime())
                    .arrivalTime(flight.getArrivalTime())
                    .price(FlightUtility.convertPriceToCurrency(flight.getPrice(), Locale.GERMANY))
                    .build();
    }

}
