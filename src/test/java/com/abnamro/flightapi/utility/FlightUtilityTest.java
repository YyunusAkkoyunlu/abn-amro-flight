package com.abnamro.flightapi.utility;

import com.abnamro.flightapi.exception.FlightRequestException;
import com.abnamro.flightapi.model.flight.Flight;
import com.abnamro.flightapi.model.flight.FlightRequest;
import com.abnamro.flightapi.model.flight.FlightResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class FlightUtilityTest {

    @Test
    void convertPriceToCurrency() {
        double price = 1234.56;
        Locale locale = Locale.US;

        String currencySymbol = Currency.getInstance(locale).getSymbol();
        String expectedValue = currencySymbol + "1,234.56";

        String convertedValue = FlightUtility.convertPriceToCurrency(price, locale);

        Assertions.assertEquals(expectedValue, convertedValue);

    }

    @Test
    void getComparatorByFieldName() {
        List<Flight> flightList = new ArrayList<>();
        flightList.add(Flight.builder().flightNumber("C101").origin("AMS").destination("BLR").departureTime(LocalTime.of(13, 00)).arrivalTime(LocalTime.of(18, 30)).price(800).build());
        flightList.add(Flight.builder().flightNumber("D101").origin("AMS").destination("MAA").departureTime(LocalTime.of(9, 00)).arrivalTime(LocalTime.of(15, 00)).price(600).build());
        flightList.add(Flight.builder().flightNumber("A101").origin("AMS").destination("DEL").departureTime(LocalTime.of(11, 00)).arrivalTime(LocalTime.of(17, 00)).price(850).build());
        flightList.add(Flight.builder().flightNumber("B101").origin("AMS").destination("BOM").departureTime(LocalTime.of(12, 00)).arrivalTime(LocalTime.of(19, 30)).price(750).build());

        Comparator<Flight> comparator = FlightUtility.getComparatorByFieldName(Flight.class, "price");
        flightList.sort(comparator);

        Assertions.assertEquals(600, flightList.get(0).getPrice());
        Assertions.assertEquals(750, flightList.get(1).getPrice());
        Assertions.assertEquals(800, flightList.get(2).getPrice());
        Assertions.assertEquals(850, flightList.get(3).getPrice());

    }

    @Test
    public void testGetComparatorByFieldName_FieldAccessException() {
        Class<?> clazz = Flight.class;
        String fieldName = "invalidField";

        FlightRequestException exception = Assertions.assertThrows(FlightRequestException.class, () ->
                FlightUtility.getComparatorByFieldName(clazz, fieldName));

        String expectedMessage = "Invalid field name: " + fieldName;
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void buildPredicates() {
        FlightRequest flightRequest = FlightRequest.builder()
                .origin("AMS")
                .price(100)
                .sortedBy("price")
                .build();

        List<Predicate<Flight>> predicates = FlightUtility.buildPredicates(flightRequest, Flight.class);

        Assertions.assertEquals(2, predicates.size());
    }

    @Test
    public void testBuildPredicates_CatchIllegalAccessException() {
        FlightRequest flightRequest = FlightRequest.builder()
                .origin("")
                .price(0)
                .sortedBy(null)
                .build();

        List<Predicate<Flight>> predicates = FlightUtility.buildPredicates(flightRequest, Flight.class);

        Assertions.assertTrue(predicates.isEmpty());
    }

    @Test
    public void testBuildPredicates_continue() {
        FlightRequest flightRequest = FlightRequest.builder()
                .origin("AMS")
                .destination("")
                .build();

        List<Predicate<Flight>> predicates = FlightUtility.buildPredicates(flightRequest, Flight.class);

        Assertions.assertEquals(1, predicates.size());
    }


/*    @Test
    public void testBuildPredicates_CatchNoSuchFieldException() {
        FlightRequest flightRequest = FlightRequest.builder()
                .origin(null)
                .price(500)
                .destination(null)
                .build();

        List<Predicate<Flight>> predicates = FlightUtility.buildPredicates(flightRequest, Flight.class);

        Assertions.assertTrue(predicates.isEmpty());
    }*/

}