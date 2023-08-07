package com.abnamro.flightapi.utility;

import com.abnamro.flightapi.exception.FlightRequestException;
import com.abnamro.flightapi.model.flight.Flight;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public class FlightUtility {

    /**
     * Converts a price value to a formatted currency string based on the provided locale.
     *
     * @param price  The price value to convert.
     * @param locale The locale used to determine the currency format.
     * @return A formatted currency string representing the price.
     */
    public static String convertPriceToCurrency(double price, Locale locale) {
        // Get the NumberFormat instance for the specified locale
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        // Format the price value using the numberFormat
        String value = numberFormat.format(new BigDecimal(price));

        // Return the formatted currency string
        return value;
    }

    /**
     * Retrieves a Comparator based on the field value specified by the field name.
     *
     * @param <T>        The type of the objects being compared.
     * @param <U>        The type of the field value being compared.
     * @param clazz      The Class object representing the class containing the field.
     * @param fieldName  The name of the field.
     * @return A Comparator based on the field value.
     * @throws FlightRequestException If the field name is invalid.
     */
    public static <T, U> Comparator<T> getComparatorByFieldName(Class<?> clazz, String fieldName) throws FlightRequestException {
        try {
            // Get the Field object for the specified field name in the class
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return Comparator.comparing(response -> {
                try {
                    // Get the value of the field in the object
                    return (Comparable) field.get(response);
                } catch (IllegalAccessException e) {
                    // Throw an exception if there is an error accessing the field
                    throw new IllegalArgumentException("Error accessing field: " + fieldName, e);
                }
            });
        } catch (NoSuchFieldException e) {
            // Throw an exception if the specified field name is invalid
            throw new FlightRequestException("Invalid field name: " + fieldName);
        }
    }

    /**
     * Builds a list of predicates based on the provided filter object and response class.
     *
     * @param filterObject  The object containing the filter parameters.
     * @param responseClass The class representing the response objects.
     * @param <T>           The type of the filter object.
     * @param <U>           The type of the response object.
     * @return A list of predicates for filtering response objects.
     */
    public static <T, U> List<Predicate<U>> buildPredicates(T filterObject, Class<U> responseClass) {
        List<Predicate<U>> predicates = new ArrayList<>();

        // Get the class and fields of the filter object and response object
        Class<?> filterClass = filterObject.getClass();
        Field[] filterFields = filterClass.getDeclaredFields();
        Field[] responseFields = responseClass.getDeclaredFields();

        for (Field filterField : filterFields) {
            filterField.setAccessible(true);
            try {
                // Get the value of the filter field
                Object fieldValue = filterField.get(filterObject);
                // Skip the field if it's a float with a value less than or equal to 0 or a String with an empty value
                if ((filterField.getType() == float.class && (float) fieldValue <= 0) || (filterField.getType() == String.class && (String) fieldValue == "")) {
                    continue;
                } else if (fieldValue != null) {
                    for (Field responseField : responseFields) {
                        // Find the matching response field with the same name as the filter field
                        if (responseField.getName().equals(filterField.getName())) {
                            // Create a predicate for comparing the filter field value with the response field value
                            Predicate<U> predicate = (obj) -> {
                                try {
                                    // Get the field in the response object with the same name as the response field
                                    Field objField = obj.getClass().getDeclaredField(responseField.getName());
                                    objField.setAccessible(true); // todo: check other approaches for this line
                                    // Get the value of the field in the response object
                                    Object objValue = objField.get(obj);
                                    // Compare the filter field value with the response field value
                                    return fieldValue.equals(objValue);
                                } catch (IllegalAccessException | NoSuchFieldException e) {
                                    e.printStackTrace();
                                    // Return false to indicate that the predicate evaluation has failed
                                    return false;
                                }
                            };

                            predicates.add(predicate); // Add the created predicate to the list of predicates
                            break; // Exit the inner loop since a matching response field has been found
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Return predicates
        return predicates;
    }

}
