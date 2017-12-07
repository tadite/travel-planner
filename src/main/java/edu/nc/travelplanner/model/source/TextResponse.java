package edu.nc.travelplanner.model.source;

public class TextResponse implements Response {

    private String text;

    public TextResponse(String text) {
        this.text = text;
    }

    @Override
    public String getRawData() {
        return text;
    }
}
