package edu.nc.travelplanner.model.factory.filter;

public class FilterParseException extends Throwable {

    public FilterParseException(Exception cause) {
        super (getErrorMessage(), cause);
    }

    private static String getErrorMessage() {
        return "Error in parse response filter!";
    }
}
