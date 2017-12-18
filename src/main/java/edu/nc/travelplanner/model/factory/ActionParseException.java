package edu.nc.travelplanner.model.factory;

public class ActionParseException extends Exception {

    public ActionParseException() {}

    public ActionParseException(String actionName)
    {
        super(getMessage(actionName));
    }

    private static String getMessage(String actionName) {
        return "Error in parse "+actionName+" action!";
    }

    public ActionParseException (Throwable cause) {
    }

    public ActionParseException (String actionName, Throwable cause) {
        super (getMessage(actionName), cause);
    }
}
