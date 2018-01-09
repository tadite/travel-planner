package edu.nc.travelplanner.model.factory.source;

public class SourceParseException extends Exception {

    public SourceParseException() {}

    public SourceParseException(String name)
    {
        super(getMessage(name));
    }

    public SourceParseException (Throwable cause) {
    }

    public SourceParseException (String name, Throwable cause) {
        super (getMessage(name), cause);
    }

    private static String getMessage(String name) {
        return "Error in parse "+name+" source!";
    }
}
