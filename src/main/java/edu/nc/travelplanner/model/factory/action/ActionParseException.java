package edu.nc.travelplanner.model.factory.action;

public class ActionParseException extends Exception {

    public ActionParseException() {}

    public ActionParseException(String actionName)
    {
        super(getMessage(actionName));
    }

    public ActionParseException (Throwable cause) {
    }

    public ActionParseException (String actionName, Throwable cause) {
        super (getMessage(actionName), cause);
    }

    private static String getMessage(String actionName) {
        return "Error in parse "+actionName+" action!";
    }
}
