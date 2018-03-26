package edu.nc.travelplanner.exception;

public class NotEnoughParamsException extends Exception {
    public NotEnoughParamsException() {
        super();
    }

    public NotEnoughParamsException(String message) {
        super(message);
    }
}