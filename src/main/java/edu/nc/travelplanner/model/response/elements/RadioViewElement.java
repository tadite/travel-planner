package edu.nc.travelplanner.model.response.elements;

public class RadioViewElement implements ViewElement {

    private String id;
    private String data;

    public RadioViewElement(String id, String data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public ViewElementType getType() {
        return ViewElementType.RADIOBOX;
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
