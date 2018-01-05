package edu.nc.travelplanner.model.source.factory;

public class DataProducerParseException extends Exception {

    public DataProducerParseException() {}

    public DataProducerParseException(String actionName)
    {
        super(getMessage(actionName));
    }

    public DataProducerParseException (Throwable cause) {
    }

    public DataProducerParseException (String actionName, Throwable cause) {
        super (getMessage(actionName), cause);
    }

    private static String getMessage(String actionName) {
        return "Error in parse "+actionName+" data producer!";
    }
}
