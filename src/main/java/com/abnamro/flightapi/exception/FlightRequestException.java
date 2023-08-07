package com.abnamro.flightapi.exception;

public class FlightRequestException extends RuntimeException {


    public FlightRequestException(String message) {
        super(message);
    }

    public FlightRequestException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
