package com.abnamro.flightapi.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for Flight-related exceptions.
 */
@RestControllerAdvice
public class FlightExceptionHandler {

    /**
     * Handles FlightRequestException and returns a ResponseEntity with error details.
     *
     * @param exception The FlightRequestException to be handled.
     * @return A ResponseEntity containing the FlightException with appropriate status and timestamp.
     */
    @ExceptionHandler(value = {FlightRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(FlightRequestException exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        // Create a FlightException object with the message from the FlightRequestException,
        // the badRequest status, and the current timestamp with the "Z" timezone.
        FlightException apiException = new FlightException(exception.getMessage(), badRequest, ZonedDateTime.now(ZoneId.of("Z")));

        // Return a ResponseEntity containing the FlightException and the badRequest status.
        return new ResponseEntity<>(apiException, badRequest);
    }

    /**
     * Handles MethodArgumentNotValidException and returns a map of field errors.
     *
     * @param exception The MethodArgumentNotValidException to be handled.
     * @return A map containing field names as keys and corresponding error messages as values.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Map<String, String> handleInvalidArguments(MethodArgumentNotValidException exception) {
        // Create a new HashMap to store field errors
        Map<String, String> errorMap = new HashMap<>();
        // Retrieve the field errors from the BindingResult of the exception
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            // Add each field error to the errorMap using the field name as key and the error message as value
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        // Return the errorMap containing field errors
        return errorMap;
    }

    /**
     * Handles DateTimeParseException and returns a ResponseEntity with an error message.
     *
     * @param exception The DateTimeParseException to be handled.
     * @return A ResponseEntity containing the error message and a Bad Request status.
     */
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> handleDateTimeParseException(DateTimeParseException exception) {
        String errorMessage = "Invalid departure time format. Please provide the time in the format HH:mm.";
        // Create a ResponseEntity with the error message and a Bad Request status
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    /**
     * Handles MismatchedInputException and returns a ResponseEntity with an error message.
     *
     * @param exception The MismatchedInputException to be handled.
     * @return A ResponseEntity containing the error message and a Bad Request status.
     */
    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<String> handleMismatchedInputException(MismatchedInputException exception) {
        String errorMessage = "Invalid price format. Please provide a valid numeric value.";
        // Create a ResponseEntity with the error message and a Bad Request status
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    
}
