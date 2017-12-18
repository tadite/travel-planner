package edu.nc.travelplanner.model.factory.tree;

public class ActionTreeParseException extends Exception{

    public ActionTreeParseException() {}

    public ActionTreeParseException(String actionTreeName)
    {
        super(getMessage(actionTreeName));
    }

    public ActionTreeParseException (Throwable cause) {
    }

    public ActionTreeParseException (String actionTreeName, Throwable cause) {
        super(getMessage(actionTreeName), cause);
    }

    private static String getMessage(String actionTreeName) {
        return "Error in parse "+actionTreeName+" action tree!";
    }
}
