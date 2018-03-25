package edu.nc.travelplanner.dto.afterPickTree;

public class ExcursionAfterPickTreeDto {
    private String name;
    private String description;
    private String price;
    private String time;
    private String booking;

    public ExcursionAfterPickTreeDto(String name, String description, String price, String time, String booking) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.time = time;
        this.booking = booking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }
}
