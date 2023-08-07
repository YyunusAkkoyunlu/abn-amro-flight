package com.abnamro.flightapi.integration.flight;

import com.abnamro.flightapi.model.flight.FlightRequest;
import com.abnamro.flightapi.model.flight.FlightResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Integration test class for Flight endpoints.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlightIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void beforeSetup() {
        baseUrl = baseUrl + ":" + port + "/api/flight";
    }

    /**
     * Test case for getting flights with parameters.
     */
    @Test
    void getFlightsWithParameters() {
        baseUrl += "/getFlightsWithParameters";

        // Prepare the FlightRequest object
        FlightRequest flightRequest = FlightRequest.builder().origin("AMS").sortedBy("price").build();

        // Send a POST request to the API endpoint with the FlightRequest object and retrieve the response
        List<FlightResponse> flightResponseList = restTemplate.postForObject(baseUrl, flightRequest, List.class);

        // Perform assertions to validate the results
        assertThat(flightResponseList.size()).isEqualTo(4);
    }

}
