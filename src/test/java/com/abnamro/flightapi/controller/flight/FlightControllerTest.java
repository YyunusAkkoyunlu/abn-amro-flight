package com.abnamro.flightapi.controller.flight;

import com.abnamro.flightapi.model.flight.FlightRequest;
import com.abnamro.flightapi.model.flight.FlightResponse;
import com.abnamro.flightapi.service.flight.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Test class for FlightController.
 */
@WebMvcTest
class FlightControllerTest {

    @MockBean
    private FlightService flightService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * This test checks if end point get correct data and response is correct.
     *
     * @throws Exception
     */
    @Test
    public void shouldGetFlightsWithParameters() throws Exception {
        // Prepare the response data as a list of FlightResponse objects
        List<FlightResponse> flightResponseList = new ArrayList<>();
        flightResponseList.add(FlightResponse.builder().flightNumber("D101").origin("AMS").destination("MAA").departureTime(LocalTime.of(9, 00)).arrivalTime(LocalTime.of(15, 00)).price("600,00 €").build());
        flightResponseList.add(FlightResponse.builder().flightNumber("B101").origin("AMS").destination("BOM").departureTime(LocalTime.of(12, 00)).arrivalTime(LocalTime.of(19, 30)).price("750,00 €").build());
        flightResponseList.add(FlightResponse.builder().flightNumber("C101").origin("AMS").destination("BLR").departureTime(LocalTime.of(13, 00)).arrivalTime(LocalTime.of(18, 30)).price("800,00 €").build());
        flightResponseList.add(FlightResponse.builder().flightNumber("A101").origin("AMS").destination("DEL").departureTime(LocalTime.of(11, 00)).arrivalTime(LocalTime.of(17, 00)).price("850,00 €").build());

        // Configure the behavior of flightService.getFlightsWithParameters method to return the prepared flightResponseList
        when(flightService.getFlightsWithParameters(any(FlightRequest.class))).thenReturn(flightResponseList);

        // Prepare the FlightRequest object
        FlightRequest flightRequest = FlightRequest.builder().origin("AMS").sortedBy("price").build();

        // Perform the API request and validate the response
        this.mockMvc.perform(post("/api/flight/getFlightsWithParameters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flightRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(flightResponseList.size())));
    }

}