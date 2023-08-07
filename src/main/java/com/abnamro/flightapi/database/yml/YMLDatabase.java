package com.abnamro.flightapi.database.yml;

import com.abnamro.flightapi.database.IDatabase;
import com.abnamro.flightapi.model.flight.Flight;
import com.abnamro.flightapi.model.flight.FlightsReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of the IDatabase interface using a YAML-based database.
 */
@Component
public class YMLDatabase implements IDatabase {

    @Autowired
    private FlightsReturn flightsReturn;


    /**
     * Retrieves all flights from the YAML database.
     *
     * @return A list of Flight objects representing all flights in the database.
     */
    @Override
    public List<Flight> getAllFlights() {
        List<Flight> flightList = flightsReturn.getCurrentFlights();

        return flightList;
    }

}
