package edu.nc.travelplanner.dto.afterPickTree;

public class FlightAfterPickTreeDto {
    private String aircraft;
    private String companyName;
    private String classType;
    private String departureDate;
    private String departureTime;
    private String timeInPath;

    public FlightAfterPickTreeDto(String aircraft, String companyName, String classType, String departureDate, String departureTime, String timeInPath) {
        this.aircraft = aircraft;
        this.companyName = companyName;
        this.classType = classType;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.timeInPath = timeInPath;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getTimeInPath() {
        return timeInPath;
    }

    public void setTimeInPath(String timeInPath) {
        this.timeInPath = timeInPath;
    }
}
