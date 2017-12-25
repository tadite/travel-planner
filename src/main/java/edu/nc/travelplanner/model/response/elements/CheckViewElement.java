package edu.nc.travelplanner.model.response.elements;

public class CheckViewElement implements ViewElement {

    private String id;
    private String data;

    public CheckViewElement(String id, String data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public ViewElementType getType() {
        return ViewElementType.CHECKBOX;
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
