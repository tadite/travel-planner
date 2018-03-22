package edu.nc.travelplanner.exception;

public class DataProducerSendException extends Exception {

    public DataProducerSendException() {}

    public DataProducerSendException(String name)
    {
        super(getMessage(name));
    }

    public DataProducerSendException (Throwable cause) {
    }

    public DataProducerSendException (String name, Throwable cause) {
        super (getMessage(name), cause);
    }

    private static String getMessage(String name) {
        return "Error in sending "+name+" !";
    }
}
