package com.abnamro.flightapi.model.flight;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class maps Flight list to flight list in application.yml file. " flight " - @ConfigurationProperties("flight") - should match " flight " in application.yml
 * Also " currentFlights " should match " currentFlights "in application.yml.
 *
 * Used @Component annotation because we can use this component from everywhere with @Autowired annotation.
 *
 * Logic is, read and map flight list from application.yml file to the " currentFlights " list on application startup. If we want to add/modify flights, we can do it in application.yml file.
 *
 */
@Component
@ConfigurationProperties("flight")
@Getter
@Setter
public class FlightsReturn {

    private List<Flight> currentFlights;

}
