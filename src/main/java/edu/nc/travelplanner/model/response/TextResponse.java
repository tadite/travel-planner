package edu.nc.travelplanner.model.response;

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
