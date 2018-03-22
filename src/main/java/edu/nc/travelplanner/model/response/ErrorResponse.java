package edu.nc.travelplanner.model.response;

public class ErrorResponse implements Response {

    private String text;

    public ErrorResponse() {
        this.text = "Произошла ошибка при получении данных или данные не найдены.";
    }

    @Override
    public String getRawData() {
        return text;
    }

    @Override
    public void setRawData(String rawData) {
        this.text=rawData;
    }
}
