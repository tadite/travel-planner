package edu.nc.travelplanner.model.response.elements;

public class DatePickerViewElement implements ViewElement {

    private String id;
    private String data;

    public DatePickerViewElement(String id, String data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public ViewElementType getType() {
        return ViewElementType.DATE_PICKER;
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
