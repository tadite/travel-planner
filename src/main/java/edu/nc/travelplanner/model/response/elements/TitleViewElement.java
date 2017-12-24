package edu.nc.travelplanner.model.response.elements;

public class TitleViewElement implements ViewElement {

    private String id;
    private String data;

    public TitleViewElement(String id, String data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public ViewElementType getType() {
        return ViewElementType.TITLE;
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
