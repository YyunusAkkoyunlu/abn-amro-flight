package com.abnamro.flightapi.service.flight;

import com.abnamro.flightapi.FlightapiApplication;
import com.abnamro.flightapi.database.IDatabase;
import com.abnamro.flightapi.database.yml.YMLDatabase;
import com.abnamro.flightapi.model.flight.FlightRequest;
import com.abnamro.flightapi.model.flight.FlightResponse;
import com.abnamro.flightapi.model.flight.FlightsReturn;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for FlightService.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = FlightapiApplication.class)
class FlightServiceTest {

    @InjectMocks
    private FlightService flightService;

    @Mock
    private YMLDatabase ymlDatabase;

    @Autowired
    private FlightsReturn flightsReturn;

    @Mock
    private IDatabase iDatabase;




    /**
     * Test case for getting flights with parameters.
     *
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void getFlightsWithParameters() throws Exception {
        // Prepare the expected flight response list
        List<FlightResponse> expectedFlightResponseList = new ArrayList<>();
        expectedFlightResponseList.add(FlightResponse.builder().flightNumber("A101").origin("AMS").destination("DEL").departureTime(LocalTime.of(11, 00)).arrivalTime(LocalTime.of(17, 00)).price("850,00 €").build());

        // Configure the behavior of ymlDatabase.getAllFlights method to return the current flights from flightsReturn
        when(ymlDatabase.getAllFlights()).thenReturn(flightsReturn.getCurrentFlights());

        // Prepare the FlightRequest object
        FlightRequest flightRequest = FlightRequest.builder().origin("AMS").departureTime(LocalTime.of(11, 00)).sortedBy("price").build();

        // Call the flightService.getFlightsWithParameters method with the flightRequest
        List<FlightResponse> flightResponseReturnList = flightService.getFlightsWithParameters(flightRequest);

        // Perform the assertions to validate the results
        assertEquals(expectedFlightResponseList.size(), flightResponseReturnList.size());
        assertEquals(expectedFlightResponseList.get(0).getDestination(), flightResponseReturnList.get(0).getDestination());
        assertEquals(expectedFlightResponseList.get(0).getArrivalTime(), flightResponseReturnList.get(0).getArrivalTime());
    }

}