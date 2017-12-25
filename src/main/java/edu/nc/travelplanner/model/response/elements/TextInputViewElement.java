package edu.nc.travelplanner.model.response.elements;

public class TextInputViewElement implements ViewElement {

    private String id;
    private String data;

    public TextInputViewElement(String id, String data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public ViewElementType getType() {
        return ViewElementType.TEXT_INPUT;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getData() {
        return data;
    }
}
