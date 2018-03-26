package edu.nc.travelplanner.dto.afterPickTree;


public class CheckpointDto {
    private String countryName;
    private String cityName;

    public CheckpointDto() {
    }

    public CheckpointDto(String countryName, String cityName) {
        this.countryName = countryName;
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}