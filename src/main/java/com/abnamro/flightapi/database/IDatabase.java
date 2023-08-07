package com.abnamro.flightapi.database;

import com.abnamro.flightapi.model.flight.Flight;

import java.util.List;

/**
 * Interface for a database
 */
public interface IDatabase {

    /**
     * Retrieves all flights from the database.
     *
     * @return A list of Flight objects representing all flights in the database.
     */
    public List<Flight> getAllFlights();

}
